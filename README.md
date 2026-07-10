# MLBB Oracle

Native Android app for MLBB screen capture + AI recognition. Captures game screen at 2 FPS, runs TFLite on-device AI to recognize heroes, items, gold, and damage, then displays a non-intrusive overlay with real-time analytics.

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.0-purple.svg)](https://kotlinlang.org/)
[![TFLite](https://img.shields.io/badge/TFLite-On--Device%20AI-orange.svg)](https://www.tensorflow.org/lite)

## Features

- **Screen Capture** — Accessibility Service captures MLBB at 2 FPS
- **AI Recognition** — TFLite models identify heroes (100+), items (200+), gold/damage (OCR)
- **Non-intrusive Overlay** — 85% opacity, draggable, doesn't intercept touch events
- **Game Booster Integration** — Auto-activates when MLBB launches (Xiaomi/OnePlus)
- **Backend API** — Stores match data for historical analytics
- **Website Dashboard** — Next.js dashboard with hero stats, match history, trends

## Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                     Android App (Kotlin)                      │
├─────────────────────────────────────────────────────────────┤
│  AccessibilityService  →  FrameProcessor  →  GameRecognizer  │
│        (2 FPS)              (STFT/OCR)       (TFLite AI)    │
│                              ↓                               │
│                        OverlayService                         │
│                   (draggable, non-touch)                      │
├─────────────────────────────────────────────────────────────┤
│  GameBoosterService  ←  MLBBReceiver  ←  Game Turbo Broadcast│
└─────────────────────────────────────────────────────────────┘
                              ↓
                    Backend API (Node.js)
                              ↓
                    Website Dashboard (Next.js)
```

## Screen Regions

| Region | Position | Purpose |
|--------|----------|---------|
| Hero Portrait | Bottom-left | Hero identification |
| Item Slots | Bottom-right | Item detection |
| Gold Display | Top-right | Gold tracking |
| Minimap | Top-left | Position tracking |
| Timer | Top-center | Match timing |

## Tech Stack

| Component | Technology |
|-----------|------------|
| Language | Kotlin 2.0 |
| UI | Jetpack Compose |
| AI | TFLite (YOLOv8-nano, CRNN, CNN) |
| Database | Room |
| Backend | Node.js + Fastify |
| Dashboard | Next.js + PostgreSQL |
| CI/CD | GitHub Actions |

## Getting Started

### Prerequisites
- Android Studio Hedgehog+
- Android SDK 34+
- Node.js 20+ (for backend)

### Build APK
```bash
git clone https://github.com/dixi3stdgdl-design/MLBBOracle.git
cd MLBBOracle
./gradlew assembleDebug
```

APK output: `app/build/outputs/apk/debug/app-debug.apk`

### Install on Device
```bash
# For OnePlus 8 (use push + install to avoid timeout)
adb push app/build/outputs/apk/debug/app-debug.apk /data/local/tmp/
adb shell pm install -r -d /data/local/tmp/app-debug.apk
```

### Backend
```bash
cd backend
npm install
npm start
```

### ML Training
```bash
cd ml
python -m venv venv
source venv/bin/activate
pip install -r requirements.txt
python train.py
```

## Configuration

### Accessibility Service
1. Go to Settings → Accessibility
2. Enable "MLBB Oracle" service
3. Launch MLBB — overlay activates automatically

### Overlay Position
- Default: Left side (x=16, y=100)
- Draggable to any position
- 85% opacity for minimal intrusion

## Testing

```bash
# Android
./gradlew test

# Backend
cd backend && npm test
```

## License

MIT License - Ver [LICENSE](LICENSE)

## Contact

[@dixi3stdgdl-design](https://github.com/dixi3stdgdl-design)
