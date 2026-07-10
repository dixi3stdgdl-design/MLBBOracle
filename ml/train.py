#!/usr/bin/env python3
"""
MLBB Oracle - Fast Training Script (PyTorch)
Simplified model for quick training on CPU
"""

import os
import json
import numpy as np
import torch
import torch.nn as nn
import torch.optim as optim
from torch.utils.data import DataLoader, Dataset
from torchvision import transforms
from PIL import Image
from sklearn.model_selection import train_test_split

# Configuration
DATA_DIR = "training_data"
IMG_SIZE = 64
BATCH_SIZE = 128
EPOCHS = 5
MODEL_DIR = "model"
DEVICE = torch.device("cpu")

class MLBBDataset(Dataset):
    def __init__(self, images, labels, transform=None):
        self.images = images
        self.labels = labels
        self.transform = transform

    def __len__(self):
        return len(self.images)

    def __getitem__(self, idx):
        image = self.images[idx]
        label = self.labels[idx]
        if self.transform:
            image = self.transform(image)
        return image, label

class SimpleCNN(nn.Module):
    """Very lightweight CNN for fast training"""
    def __init__(self, num_classes):
        super(SimpleCNN, self).__init__()

        self.features = nn.Sequential(
            nn.Conv2d(3, 16, 3, padding=1),
            nn.ReLU(inplace=True),
            nn.MaxPool2d(2, 2),
            nn.Conv2d(16, 32, 3, padding=1),
            nn.ReLU(inplace=True),
            nn.MaxPool2d(2, 2),
            nn.Conv2d(32, 64, 3, padding=1),
            nn.ReLU(inplace=True),
            nn.MaxPool2d(2, 2),
        )

        self.classifier = nn.Sequential(
            nn.Flatten(),
            nn.Linear(64 * 8 * 8, 128),
            nn.ReLU(inplace=True),
            nn.Linear(128, num_classes)
        )

    def forward(self, x):
        x = self.features(x)
        x = self.classifier(x)
        return x

def load_images_fast(directory, max_per_class=50):
    """Load images quickly with minimal processing"""
    images = []
    labels = []
    class_names = sorted([d for d in os.listdir(directory)
                         if os.path.isdir(os.path.join(directory, d))])

    print(f"  Classes: {len(class_names)}")

    for class_idx, class_name in enumerate(class_names):
        class_dir = os.path.join(directory, class_name)
        img_files = [f for f in os.listdir(class_dir) if f.endswith('.png')][:max_per_class]

        for img_file in img_files:
            img_path = os.path.join(class_dir, img_file)
            try:
                img = Image.open(img_path).convert('RGB').resize((IMG_SIZE, IMG_SIZE))
                images.append(np.array(img, dtype=np.float32) / 255.0)
                labels.append(class_idx)
            except:
                pass

    return np.array(images), np.array(labels), class_names

def train_quick(model, train_loader, val_loader, name):
    """Quick training loop"""
    print(f"\nTraining {name}...")
    criterion = nn.CrossEntropyLoss()
    optimizer = optim.Adam(model.parameters(), lr=0.001)

    for epoch in range(EPOCHS):
        model.train()
        correct = total = 0

        for images, labels in train_loader:
            optimizer.zero_grad()
            outputs = model(images)
            loss = criterion(outputs, labels)
            loss.backward()
            optimizer.step()
            _, pred = outputs.max(1)
            total += labels.size(0)
            correct += pred.eq(labels).sum().item()

        # Validation
        model.eval()
        val_correct = val_total = 0
        with torch.no_grad():
            for images, labels in val_loader:
                outputs = model(images)
                _, pred = outputs.max(1)
                val_total += labels.size(0)
                val_correct += pred.eq(labels).sum().item()

        train_acc = 100. * correct / total
        val_acc = 100. * val_correct / val_total
        print(f"  Epoch {epoch+1}: Train {train_acc:.1f}% | Val {val_acc:.1f}%")

    return model

def main():
    print("=" * 60)
    print("MLBB Oracle - Fast Training")
    print("=" * 60)

    os.makedirs(MODEL_DIR, exist_ok=True)

    transform = transforms.Compose([
        transforms.ToPILImage(),
        transforms.RandomHorizontalFlip(),
        transforms.ToTensor(),
    ])

    results = {}

    # Train Hero Model
    print("\n[1/3] HERO MODEL")
    X, y, classes = load_images_fast(f"{DATA_DIR}/heroes", max_per_class=30)
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, stratify=y)
    X_train, X_val, y_train, y_val = train_test_split(X_train, y_train, test_size=0.15, stratify=y_train)

    loader_tr = DataLoader(MLBBDataset(X_train, y_train, transform), batch_size=BATCH_SIZE, shuffle=True)
    loader_va = DataLoader(MLBBDataset(X_val, y_val, transform), batch_size=BATCH_SIZE)
    loader_te = DataLoader(MLBBDataset(X_test, y_test, transform), batch_size=BATCH_SIZE)

    hero_model = SimpleCNN(len(classes))
    hero_model = train_quick(hero_model, loader_tr, loader_va, "Hero")

    # Evaluate
    hero_model.eval()
    correct = sum(hero_model(x).max(1)[1].eq(y).sum().item() for x, y in loader_te)
    results["hero"] = 100. * correct / len(y_test)
    print(f"  Test Accuracy: {results['hero']:.1f}%")

    torch.save({"state": hero_model.state_dict(), "classes": classes}, f"{MODEL_DIR}/hero_model.pth")
    with open(f"{MODEL_DIR}/hero_labels.txt", "w") as f:
        f.write("\n".join(classes))

    # Train Item Model
    print("\n[2/3] ITEM MODEL")
    X, y, classes = load_images_fast(f"{DATA_DIR}/items", max_per_class=30)
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, stratify=y)
    X_train, X_val, y_train, y_val = train_test_split(X_train, y_train, test_size=0.15, stratify=y_train)

    loader_tr = DataLoader(MLBBDataset(X_train, y_train, transform), batch_size=BATCH_SIZE, shuffle=True)
    loader_va = DataLoader(MLBBDataset(X_val, y_val, transform), batch_size=BATCH_SIZE)
    loader_te = DataLoader(MLBBDataset(X_test, y_test, transform), batch_size=BATCH_SIZE)

    item_model = SimpleCNN(len(classes))
    item_model = train_quick(item_model, loader_tr, loader_va, "Item")

    item_model.eval()
    correct = sum(item_model(x).max(1)[1].eq(y).sum().item() for x, y in loader_te)
    results["item"] = 100. * correct / len(y_test)
    print(f"  Test Accuracy: {results['item']:.1f}%")

    torch.save({"state": item_model.state_dict(), "classes": classes}, f"{MODEL_DIR}/item_model.pth")
    with open(f"{MODEL_DIR}/item_labels.txt", "w") as f:
        f.write("\n".join(classes))

    # Train Number Model
    print("\n[3/3] NUMBER MODEL")
    X, y, classes = load_images_fast(f"{DATA_DIR}/numbers", max_per_class=100)
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, stratify=y)
    X_train, X_val, y_train, y_val = train_test_split(X_train, y_train, test_size=0.15, stratify=y_train)

    loader_tr = DataLoader(MLBBDataset(X_train, y_train, transform), batch_size=BATCH_SIZE, shuffle=True)
    loader_va = DataLoader(MLBBDataset(X_val, y_val, transform), batch_size=BATCH_SIZE)
    loader_te = DataLoader(MLBBDataset(X_test, y_test, transform), batch_size=BATCH_SIZE)

    num_model = SimpleCNN(len(classes))
    num_model = train_quick(num_model, loader_tr, loader_va, "Number")

    num_model.eval()
    correct = sum(num_model(x).max(1)[1].eq(y).sum().item() for x, y in loader_te)
    results["number"] = 100. * correct / len(y_test)
    print(f"  Test Accuracy: {results['number']:.1f}%")

    torch.save({"state": num_model.state_dict(), "classes": classes}, f"{MODEL_DIR}/number_model.pth")
    with open(f"{MODEL_DIR}/number_labels.txt", "w") as f:
        f.write("\n".join(classes))

    # Summary
    print("\n" + "=" * 60)
    print("TRAINING COMPLETE!")
    print(f"  Hero: {results['hero']:.1f}% | Item: {results['item']:.1f}% | Number: {results['number']:.1f}%")
    print("=" * 60)

    with open(f"{MODEL_DIR}/training_summary.json", "w") as f:
        json.dump(results, f, indent=2)

if __name__ == "__main__":
    main()
