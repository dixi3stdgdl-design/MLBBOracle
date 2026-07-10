# MLBB Oracle - ML Training Pipeline

This directory contains the machine learning training pipeline for the MLBB Oracle Android app. The pipeline generates synthetic training data and trains a lightweight TFLite model for recognizing heroes, items, and game numbers.

## Overview

The ML model performs three tasks:
1. **Hero Recognition** - Classifies 60+ MLBB heroes from screen captures
2. **Item Recognition** - Identifies 50+ items from inventory/equipment screens
3. **Number OCR** - Reads gold values, damage numbers, and other numeric displays

## Architecture

- **Model Type**: Lightweight CNN (MobileNetV2-based)
- **Output Format**: TFLite (quantized INT8)
- **Target Size**: <5MB
- **Target Accuracy**: >80% on synthetic data

## Files

| File | Description |
|------|-------------|
| `generate_synthetic_data.py` | Generates synthetic training images using PIL |
| `train.py` | Main training script for all three tasks |
| `convert_to_tflite.py` | Converts trained model to quantized TFLite |
| `export_to_android.sh` | Copies model files to Android assets |
| `requirements.txt` | Python dependencies |

## Quick Start

```bash
# Install dependencies
pip3 install -r requirements.txt

# Generate synthetic training data
python3 generate_synthetic_data.py

# Train the model
python3 train.py

# Convert to TFLite
python3 convert_to_tflite.py

# Export to Android app
bash export_to_android.sh
```

## Training Data

The synthetic data generator creates realistic-looking game UI elements:

### Hero Portraits
- Colored rectangles with gradient backgrounds
- Hero name text overlay
- Role icon (tank/mage/marksman/assassin/support/fighter)
- Random rotation, brightness, and noise augmentation

### Item Icons
- Distinct shapes per item category (weapon/defense/magic/boots/jungle)
- Category-specific color schemes
- Item name text overlay
- Various augmentation effects

### Gold/Damage Numbers
- White text on dark backgrounds
- Multiple font sizes and styles
- Random positioning within bounds
- Noise and blur augmentation

## Data Augmentation

Applied during training:
- Random rotation (±15°)
- Brightness adjustment (0.7-1.3)
- Gaussian noise
- Random cropping
- Horizontal flip (for non-text data)

## Export Details

The final model files:
- `mlbb_model.tflite` - Main classification model (<5MB)
- `hero_labels.txt` - Hero class labels
- `item_labels.txt` - Item class labels

These are copied to `app/src/main/assets/` for Android deployment.

## Model Integration

In the Android app, load the model in `GameRecognizer.kt`:

```kotlin
val model = MlbbModel.newInstance(context)
val input = TensorBuffer.createFixedSize(intArrayOf(1, 64, 64, 3), DataType.FLOAT32)
// ... populate input tensor
val output = model.process(input)
// ... parse results
model.close()
```

## Performance

On synthetic data:
- Hero classification: ~85-90% accuracy
- Item classification: ~85-90% accuracy
- Number OCR: ~90-95% accuracy

Note: Real-world performance will vary. Fine-tuning with actual game screenshots is recommended for production use.
