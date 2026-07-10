#!/usr/bin/env python3
"""
MLBB Oracle - Training Script (PyTorch)
Trains lightweight CNN for hero/item recognition and number OCR
"""

import os
import json
import numpy as np
import torch
import torch.nn as nn
import torch.optim as optim
from torch.utils.data import DataLoader, Dataset
from PIL import Image

DATA_DIR = "training_data"
IMG_SIZE = 64
BATCH_SIZE = 32
EPOCHS = 8
MODEL_DIR = "model"

class SimpleDataset(Dataset):
    def __init__(self, images, labels):
        self.images = torch.FloatTensor(images).permute(0, 3, 1, 2) / 255.0
        self.labels = torch.LongTensor(labels)
    def __len__(self):
        return len(self.labels)
    def __getitem__(self, idx):
        return self.images[idx], self.labels[idx]

class LiteCNN(nn.Module):
    def __init__(self, num_classes):
        super().__init__()
        self.net = nn.Sequential(
            nn.Conv2d(3, 32, 3, padding=1), nn.ReLU(), nn.MaxPool2d(2),
            nn.Conv2d(32, 64, 3, padding=1), nn.ReLU(), nn.MaxPool2d(2),
            nn.Conv2d(64, 64, 3, padding=1), nn.ReLU(), nn.MaxPool2d(2),
            nn.Flatten(),
            nn.Linear(64 * 8 * 8, 128), nn.ReLU(),
            nn.Linear(128, num_classes)
        )
    def forward(self, x):
        return self.net(x)

def load_data(directory, max_per_class=20):
    images, labels, classes = [], [], sorted([
        d for d in os.listdir(directory) if os.path.isdir(os.path.join(directory, d))
    ])
    print(f"  {len(classes)} classes")

    for idx, cls in enumerate(classes):
        cls_dir = os.path.join(directory, cls)
        files = [f for f in os.listdir(cls_dir) if f.endswith('.png')][:max_per_class]
        for f in files:
            try:
                img = Image.open(os.path.join(cls_dir, f)).convert('RGB').resize((IMG_SIZE, IMG_SIZE))
                images.append(np.array(img))
                labels.append(idx)
            except: pass
    return np.array(images), np.array(labels), classes

def train_model(model, tr_loader, va_loader, epochs=EPOCHS):
    opt = optim.Adam(model.parameters(), lr=0.001)
    crit = nn.CrossEntropyLoss()
    best_acc, best_state = 0, None

    for ep in range(epochs):
        model.train()
        for x, y in tr_loader:
            opt.zero_grad()
            crit(model(x), y).backward()
            opt.step()

        model.eval()
        correct = total = 0
        with torch.no_grad():
            for x, y in va_loader:
                correct += model(x).argmax(1).eq(y).sum().item()
                total += y.size(0)
        acc = 100 * correct / total
        if acc > best_acc:
            best_acc, best_state = acc, {k: v.clone() for k, v in model.state_dict().items()}
        print(f"  Epoch {ep+1}: {acc:.1f}%")

    model.load_state_dict(best_state)
    return model

def evaluate(model, loader):
    model.eval()
    correct = total = 0
    with torch.no_grad():
        for x, y in loader:
            correct += model(x).argmax(1).eq(y).sum().item()
            total += y.size(0)
    return 100 * correct / total

def main():
    print("=" * 50)
    print("MLBB Oracle Training")
    print("=" * 50)
    os.makedirs(MODEL_DIR, exist_ok=True)
    results = {}

    for name, path, max_pc in [("Hero", "heroes", 20), ("Item", "items", 20), ("Number", "numbers", 50)]:
        print(f"\n[{name}]")
        X, y, classes = load_data(f"{DATA_DIR}/{path}", max_pc)

        n = len(X)
        idx = np.random.permutation(n)
        split = int(0.8 * n)
        tr_idx, te_idx = idx[:split], idx[split:]
        split2 = int(0.85 * len(tr_idx))
        va_idx = tr_idx[split2:]
        tr_idx = tr_idx[:split2]

        tr_loader = DataLoader(SimpleDataset(X[tr_idx], y[tr_idx]), batch_size=BATCH_SIZE, shuffle=True)
        va_loader = DataLoader(SimpleDataset(X[va_idx], y[va_idx]), batch_size=BATCH_SIZE)
        te_loader = DataLoader(SimpleDataset(X[te_idx], y[te_idx]), batch_size=BATCH_SIZE)

        model = LiteCNN(len(classes))
        model = train_model(model, tr_loader, va_loader)
        acc = evaluate(model, te_loader)
        results[name.lower()] = acc
        print(f"  Test: {acc:.1f}%")

        torch.save({"state": model.state_dict(), "classes": classes}, f"{MODEL_DIR}/{name.lower()}_model.pth")
        with open(f"{MODEL_DIR}/{name.lower()}_labels.txt", "w") as f:
            f.write("\n".join(classes))

    print("\n" + "=" * 50)
    for k, v in results.items():
        print(f"  {k}: {v:.1f}%")
    print("=" * 50)

    with open(f"{MODEL_DIR}/training_summary.json", "w") as f:
        json.dump(results, f, indent=2)

if __name__ == "__main__":
    main()
