#!/bin/bash
# MLBB Oracle - Export Models to Android Assets
# Copies trained TFLite models to the Android app's assets directory

set -e  # Exit on error

# Configuration
TFLITE_DIR="tflite_output"
ANDROID_ASSETS_DIR="../app/src/main/assets"

echo "============================================================"
echo "MLBB Oracle - Exporting Models to Android"
echo "============================================================"

# Check if tflite output directory exists
if [ ! -d "$TFLITE_DIR" ]; then
    echo "ERROR: TFLite output directory not found: $TFLITE_DIR"
    echo "Please run convert_to_tflite.py first"
    exit 1
fi

# Create Android assets directory if it doesn't exist
mkdir -p "$ANDROID_ASSETS_DIR"

echo ""
echo "Copying model files to Android assets..."

# Copy TFLite models
for model in hero_model item_model number_model; do
    if [ -f "$TFLITE_DIR/${model}.tflite" ]; then
        cp "$TFLITE_DIR/${model}.tflite" "$ANDROID_ASSETS_DIR/"
        echo "  ✓ Copied ${model}.tflite"
    else
        echo "  ✗ ${model}.tflite not found, skipping"
    fi
done

# Copy label files
for labels in hero_labels item_labels number_labels; do
    if [ -f "$TFLITE_DIR/${labels}.txt" ]; then
        cp "$TFLITE_DIR/${labels}.txt" "$ANDROID_ASSETS_DIR/"
        echo "  ✓ Copied ${labels}.txt"
    else
        echo "  ✗ ${labels}.txt not found, skipping"
    fi
done

# Copy model metadata
if [ -f "$TFLITE_DIR/model_metadata.json" ]; then
    cp "$TFLITE_DIR/model_metadata.json" "$ANDROID_ASSETS_DIR/"
    echo "  ✓ Copied model_metadata.json"
fi

echo ""
echo "============================================================"
echo "Export Complete!"
echo "============================================================"
echo ""
echo "Files exported to: $ANDROID_ASSETS_DIR/"
echo ""

# List exported files
echo "Exported files:"
ls -lh "$ANDROID_ASSETS_DIR/" | grep -E '\.(tflite|txt|json)$'

echo ""
echo "============================================================"
echo "Next steps:"
echo "  1. Rebuild the Android app"
echo "  2. Test model loading in GameRecognizer.kt"
echo "============================================================"
