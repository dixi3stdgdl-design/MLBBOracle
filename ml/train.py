#!/usr/bin/env python3
"""
MLBB Oracle - ML Training Script (PyTorch - Optimized)
Trains a lightweight CNN for hero/item recognition and number OCR
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
BATCH_SIZE = 64
EPOCHS = 10
MODEL_DIR = "model"
DEVICE = torch.device("cpu")

class MLBBDataset(Dataset):
    """Custom dataset for MLBB images"""
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

class LiteCNN(nn.Module):
    """Lightweight CNN for classification"""
    def __init__(self, num_classes):
        super(LiteCNN, self).__init__()

        self.features = nn.Sequential(
            # Block 1
            nn.Conv2d(3, 32, kernel_size=3, padding=1),
            nn.BatchNorm2d(32),
            nn.ReLU(inplace=True),
            nn.MaxPool2d(2, 2),

            # Block 2
            nn.Conv2d(32, 64, kernel_size=3, padding=1),
            nn.BatchNorm2d(64),
            nn.ReLU(inplace=True),
            nn.MaxPool2d(2, 2),

            # Block 3
            nn.Conv2d(64, 128, kernel_size=3, padding=1),
            nn.BatchNorm2d(128),
            nn.ReLU(inplace=True),
            nn.MaxPool2d(2, 2),
        )

        self.classifier = nn.Sequential(
            nn.Flatten(),
            nn.Dropout(0.5),
            nn.Linear(128 * 8 * 8, 256),
            nn.ReLU(inplace=True),
            nn.Dropout(0.3),
            nn.Linear(256, num_classes)
        )

    def forward(self, x):
        x = self.features(x)
        x = self.classifier(x)
        return x

def load_images_from_dir(directory, max_per_class=None):
    """Load images from directory structure (class_name/image.png)"""
    images = []
    labels = []
    class_names = sorted([d for d in os.listdir(directory)
                         if os.path.isdir(os.path.join(directory, d))])

    print(f"  Found {len(class_names)} classes in {directory}")

    for class_idx, class_name in enumerate(class_names):
        class_dir = os.path.join(directory, class_name)
        img_files = [f for f in os.listdir(class_dir) if f.endswith(('.png', '.jpg', '.jpeg'))]

        if max_per_class:
            img_files = img_files[:max_per_class]

        for img_file in img_files:
            img_path = os.path.join(class_dir, img_file)
            try:
                img = Image.open(img_path).convert('RGB')
                img = img.resize((IMG_SIZE, IMG_SIZE))
                images.append(np.array(img))
                labels.append(class_idx)
            except Exception as e:
                print(f"    Error loading {img_path}: {e}")

    return np.array(images), np.array(labels), class_names

def load_number_data(directory):
    """Load number classification data"""
    images = []
    labels = []
    class_names = sorted([d for d in os.listdir(directory)
                         if os.path.isdir(os.path.join(directory, d))])

    print(f"  Found {len(class_names)} number classes")

    for class_idx, class_name in enumerate(class_names):
        class_dir = os.path.join(directory, class_name)
        img_files = [f for f in os.listdir(class_dir) if f.endswith(('.png', '.jpg', '.jpeg'))]

        # Balance classes
        max_per_class = min(len(img_files), 200)
        img_files = img_files[:max_per_class]

        for img_file in img_files:
            img_path = os.path.join(class_dir, img_file)
            try:
                img = Image.open(img_path).convert('RGB')
                img = img.resize((IMG_SIZE, IMG_SIZE))
                images.append(np.array(img))
                labels.append(class_idx)
            except Exception as e:
                print(f"    Error loading {img_path}: {e}")

    return np.array(images), np.array(labels), class_names

def train_model(model, train_loader, val_loader, model_name, epochs=EPOCHS):
    """Train a model"""
    print(f"\nTraining {model_name}...")
    print(f"  Train samples: {len(train_loader.dataset)}")
    print(f"  Val samples: {len(val_loader.dataset)}")

    criterion = nn.CrossEntropyLoss()
    optimizer = optim.Adam(model.parameters(), lr=0.001)
    scheduler = optim.lr_scheduler.ReduceLROnPlateau(optimizer, mode='min', patience=2, factor=0.5)

    best_val_acc = 0.0
    best_model_state = None

    for epoch in range(epochs):
        # Training phase
        model.train()
        train_loss = 0.0
        train_correct = 0
        train_total = 0

        for images, labels in train_loader:
            images, labels = images.to(DEVICE), labels.to(DEVICE)

            optimizer.zero_grad()
            outputs = model(images)
            loss = criterion(outputs, labels)
            loss.backward()
            optimizer.step()

            train_loss += loss.item()
            _, predicted = outputs.max(1)
            train_total += labels.size(0)
            train_correct += predicted.eq(labels).sum().item()

        train_loss /= len(train_loader)
        train_acc = 100. * train_correct / train_total

        # Validation phase
        model.eval()
        val_loss = 0.0
        val_correct = 0
        val_total = 0

        with torch.no_grad():
            for images, labels in val_loader:
                images, labels = images.to(DEVICE), labels.to(DEVICE)
                outputs = model(images)
                loss = criterion(outputs, labels)

                val_loss += loss.item()
                _, predicted = outputs.max(1)
                val_total += labels.size(0)
                val_correct += predicted.eq(labels).sum().item()

        val_loss /= len(val_loader)
        val_acc = 100. * val_correct / val_total

        scheduler.step(val_loss)

        print(f"  Epoch [{epoch+1}/{epochs}] "
              f"Train Loss: {train_loss:.4f} Acc: {train_acc:.2f}% | "
              f"Val Loss: {val_loss:.4f} Acc: {val_acc:.2f}%")

        # Save best model
        if val_acc > best_val_acc:
            best_val_acc = val_acc
            best_model_state = model.state_dict().copy()

    # Load best model
    model.load_state_dict(best_model_state)
    print(f"  Best validation accuracy: {best_val_acc:.2f}%")

    return model, best_val_acc

def evaluate_model(model, test_loader, class_names, model_name):
    """Evaluate model on test set"""
    print(f"\nEvaluating {model_name}...")

    model.eval()
    correct = 0
    total = 0

    with torch.no_grad():
        for images, labels in test_loader:
            images, labels = images.to(DEVICE), labels.to(DEVICE)
            outputs = model(images)
            _, predicted = outputs.max(1)

            total += labels.size(0)
            correct += predicted.eq(labels).sum().item()

    accuracy = 100. * correct / total
    print(f"  Test Accuracy: {accuracy:.2f}%")

    return accuracy

def save_model(model, class_names, model_name, output_dir):
    """Save model and class labels"""
    os.makedirs(output_dir, exist_ok=True)

    # Save PyTorch model
    model_path = os.path.join(output_dir, f"{model_name}.pth")
    torch.save({
        'model_state_dict': model.state_dict(),
        'num_classes': len(class_names),
        'img_size': IMG_SIZE
    }, model_path)
    print(f"  Saved model to {model_path}")

    # Save labels
    labels_path = os.path.join(output_dir, f"{model_name}_labels.txt")
    with open(labels_path, 'w') as f:
        for name in class_names:
            f.write(f"{name}\n")
    print(f"  Saved labels to {labels_path}")

def main():
    """Main training function"""
    print("=" * 60)
    print("MLBB Oracle - Model Training (PyTorch)")
    print("=" * 60)

    os.makedirs(MODEL_DIR, exist_ok=True)

    # Data transforms
    train_transform = transforms.Compose([
        transforms.ToPILImage(),
        transforms.RandomRotation(10),
        transforms.RandomHorizontalFlip(),
        transforms.ColorJitter(brightness=0.2, contrast=0.2),
        transforms.ToTensor(),
        transforms.Normalize(mean=[0.485, 0.456, 0.406], std=[0.229, 0.224, 0.225])
    ])

    val_transform = transforms.Compose([
        transforms.ToPILImage(),
        transforms.ToTensor(),
        transforms.Normalize(mean=[0.485, 0.456, 0.406], std=[0.229, 0.224, 0.225])
    ])

    results = {}

    # =====================================================
    # 1. Train Hero Recognition Model
    # =====================================================
    print("\n" + "=" * 60)
    print("1. HERO RECOGNITION MODEL")
    print("=" * 60)

    print("\nLoading hero data...")
    X_hero, y_hero, hero_classes = load_images_from_dir(
        f"{DATA_DIR}/heroes",
        max_per_class=200
    )

    # Split data
    X_train, X_test, y_train, y_test = train_test_split(
        X_hero, y_hero, test_size=0.2, random_state=42, stratify=y_hero
    )
    X_train, X_val, y_train, y_val = train_test_split(
        X_train, y_train, test_size=0.15, random_state=42, stratify=y_train
    )

    # Create datasets and loaders
    train_dataset = MLBBDataset(X_train, y_train, train_transform)
    val_dataset = MLBBDataset(X_val, y_val, val_transform)
    test_dataset = MLBBDataset(X_test, y_test, val_transform)

    train_loader = DataLoader(train_dataset, batch_size=BATCH_SIZE, shuffle=True, num_workers=2)
    val_loader = DataLoader(val_dataset, batch_size=BATCH_SIZE, shuffle=False, num_workers=2)
    test_loader = DataLoader(test_dataset, batch_size=BATCH_SIZE, shuffle=False, num_workers=2)

    # Create and train model
    hero_model = LiteCNN(len(hero_classes)).to(DEVICE)

    hero_model, hero_val_acc = train_model(hero_model, train_loader, val_loader, "Hero Model")
    hero_accuracy = evaluate_model(hero_model, test_loader, hero_classes, "Hero Model")

    save_model(hero_model, hero_classes, "hero_model", MODEL_DIR)
    results["hero_accuracy"] = hero_accuracy

    # =====================================================
    # 2. Train Item Recognition Model
    # =====================================================
    print("\n" + "=" * 60)
    print("2. ITEM RECOGNITION MODEL")
    print("=" * 60)

    print("\nLoading item data...")
    X_item, y_item, item_classes = load_images_from_dir(
        f"{DATA_DIR}/items",
        max_per_class=200
    )

    # Split data
    X_train, X_test, y_train, y_test = train_test_split(
        X_item, y_item, test_size=0.2, random_state=42, stratify=y_item
    )
    X_train, X_val, y_train, y_val = train_test_split(
        X_train, y_train, test_size=0.15, random_state=42, stratify=y_train
    )

    # Create datasets and loaders
    train_dataset = MLBBDataset(X_train, y_train, train_transform)
    val_dataset = MLBBDataset(X_val, y_val, val_transform)
    test_dataset = MLBBDataset(X_test, y_test, val_transform)

    train_loader = DataLoader(train_dataset, batch_size=BATCH_SIZE, shuffle=True, num_workers=2)
    val_loader = DataLoader(val_dataset, batch_size=BATCH_SIZE, shuffle=False, num_workers=2)
    test_loader = DataLoader(test_dataset, batch_size=BATCH_SIZE, shuffle=False, num_workers=2)

    # Create and train model
    item_model = LiteCNN(len(item_classes)).to(DEVICE)

    item_model, item_val_acc = train_model(item_model, train_loader, val_loader, "Item Model")
    item_accuracy = evaluate_model(item_model, test_loader, item_classes, "Item Model")

    save_model(item_model, item_classes, "item_model", MODEL_DIR)
    results["item_accuracy"] = item_accuracy

    # =====================================================
    # 3. Train Number Classification Model
    # =====================================================
    print("\n" + "=" * 60)
    print("3. NUMBER CLASSIFICATION MODEL")
    print("=" * 60)

    print("\nLoading number data...")
    X_num, y_num, num_classes = load_number_data(f"{DATA_DIR}/numbers")

    # Split data
    X_train, X_test, y_train, y_test = train_test_split(
        X_num, y_num, test_size=0.2, random_state=42, stratify=y_num
    )
    X_train, X_val, y_train, y_val = train_test_split(
        X_train, y_train, test_size=0.15, random_state=42, stratify=y_train
    )

    # Create datasets and loaders
    train_dataset = MLBBDataset(X_train, y_train, train_transform)
    val_dataset = MLBBDataset(X_val, y_val, val_transform)
    test_dataset = MLBBDataset(X_test, y_test, val_transform)

    train_loader = DataLoader(train_dataset, batch_size=BATCH_SIZE, shuffle=True, num_workers=2)
    val_loader = DataLoader(val_dataset, batch_size=BATCH_SIZE, shuffle=False, num_workers=2)
    test_loader = DataLoader(test_dataset, batch_size=BATCH_SIZE, shuffle=False, num_workers=2)

    # Create and train model
    num_model = LiteCNN(len(num_classes)).to(DEVICE)

    num_model, num_val_acc = train_model(num_model, train_loader, val_loader, "Number Model")
    num_accuracy = evaluate_model(num_model, test_loader, num_classes, "Number Model")

    save_model(num_model, num_classes, "number_model", MODEL_DIR)
    results["number_accuracy"] = num_accuracy

    # =====================================================
    # Summary
    # =====================================================
    print("\n" + "=" * 60)
    print("TRAINING COMPLETE!")
    print("=" * 60)
    print(f"\nModel Performance:")
    print(f"  Hero Recognition:   {results['hero_accuracy']:.2f}% accuracy")
    print(f"  Item Recognition:   {results['item_accuracy']:.2f}% accuracy")
    print(f"  Number Classification: {results['number_accuracy']:.2f}% accuracy")
    print(f"\nModels saved to: {MODEL_DIR}/")
    print("=" * 60)

    # Save training summary
    summary = {
        "hero_accuracy": float(results['hero_accuracy']),
        "item_accuracy": float(results['item_accuracy']),
        "number_accuracy": float(results['number_accuracy']),
        "hero_classes": len(hero_classes),
        "item_classes": len(item_classes),
        "number_classes": len(num_classes),
        "img_size": IMG_SIZE
    }

    with open(f"{MODEL_DIR}/training_summary.json", "w") as f:
        json.dump(summary, f, indent=2)

    print(f"\nTraining summary saved to {MODEL_DIR}/training_summary.json")

if __name__ == "__main__":
    main()
