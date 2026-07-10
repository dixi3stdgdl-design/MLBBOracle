#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_DIR="$(dirname "$SCRIPT_DIR")"
KEYSTORE_FILE="${KEYSTORE_FILE:-$PROJECT_DIR/release.keystore}"
APK_DIR="$PROJECT_DIR/app/build/outputs/apk/release"

cd "$PROJECT_DIR"

# Check keystore exists
if [ ! -f "$KEYSTORE_FILE" ]; then
    echo "Error: Keystore not found at $KEYSTORE_FILE"
    echo "Set KEYSTORE_FILE env var or place release.keystore in project root."
    exit 1
fi

echo "==> Building release APK..."
./gradlew assembleRelease --no-daemon

UNSIGNED_APK="$APK_DIR/app-release-unsigned.apk"
SIGNED_APK="$APK_DIR/app-release.apk"

if [ ! -f "$UNSIGNED_APK" ]; then
    echo "Error: Unsigned APK not found at $UNSIGNED_APK"
    exit 1
fi

echo "==> Signing APK..."
read -rp "Key alias: " KEY_ALIAS
read -rsp "Keystore password: " KEYSTORE_PASSWORD && echo
read -rsp "Key password: " KEY_PASSWORD && echo

jarsigner -verbose -sigalg SHA256withRSA -digestalg SHA-256 \
    -keystore "$KEYSTORE_FILE" \
    -storepass "$KEYSTORE_PASSWORD" \
    -keypass "$KEY_PASSWORD" \
    "$UNSIGNED_APK" \
    "$KEY_ALIAS"

# Zipalign
BUILD_TOOLS="$ANDROID_HOME/build-tools/$(ls "$ANDROID_HOME/build-tools/" | sort -V | tail -1)"
"$BUILD_TOOLS/zipalign" -v -p 4 "$UNSIGNED_APK" "$SIGNED_APK"

echo "==> Release APK ready: $SIGNED_APK"
