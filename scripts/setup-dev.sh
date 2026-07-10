#!/usr/bin/env bash
set -euo pipefail

RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m'

check() {
    local name="$1" cmd="$2" min="$3"
    if command -v "$cmd" &>/dev/null; then
        local ver
        ver=$($cmd --version 2>/dev/null | head -1 || echo "unknown")
        echo -e "${GREEN}[OK]${NC} $name ($ver)"
        return 0
    else
        echo -e "${RED}[MISSING]${NC} $name (need $min+)"
        return 1
    fi
}

echo "=== MLBB Oracle Dev Setup ==="
echo ""

ISSUES=0

# JDK 17
if check "JDK 17" "java" "17"; then
    JAVA_VER=$(java -version 2>&1 | head -1 | grep -oP '\d+' | head -1 || echo "0")
    if [ "$JAVA_VER" -lt 17 ] 2>/dev/null; then
        echo -e "  ${YELLOW}Warning: Java version is $JAVA_VER, need 17+${NC}"
        ISSUES=$((ISSUES + 1))
    fi
else
    ISSUES=$((ISSUES + 1))
fi

# Android SDK
if [ -n "${ANDROID_HOME:-}" ] && [ -d "${ANDROID_HOME:-/dev/null}" ]; then
    echo -e "${GREEN}[OK]${NC} Android SDK ($ANDROID_HOME)"
else
    echo -e "${RED}[MISSING]${NC} Android SDK (set ANDROID_HOME)"
    ISSUES=$((ISSUES + 1))
fi

# Node.js
check "Node.js 20+" "node" "20" || ISSUES=$((ISSUES + 1))

# Python 3.11
check "Python 3.11+" "python3" "3.11" || ISSUES=$((ISSUES + 1))

# Gradle wrapper
if [ -x "./gradlew" ]; then
    echo -e "${GREEN}[OK]${NC} Gradle wrapper"
else
    echo -e "${YELLOW}[WARN]${NC} Gradle wrapper not executable (run: chmod +x gradlew)"
fi

echo ""
if [ "$ISSUES" -eq 0 ]; then
    echo -e "${GREEN}All checks passed!${NC}"
else
    echo -e "${YELLOW}$ISSUES issue(s) found. Fix them before developing.${NC}"
    exit 1
fi
