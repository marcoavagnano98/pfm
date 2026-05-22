#!/usr/bin/env sh
set -eu

sh scripts/static-check.sh

if ! command -v java >/dev/null 2>&1; then
    echo "Android unit tests and debug build skipped: java is not installed."
    exit 0
fi

sdk_dir="${ANDROID_SDK_ROOT:-${ANDROID_HOME:-}}"
if [ -z "$sdk_dir" ] && [ -d "$HOME/Android/Sdk" ]; then
    sdk_dir="$HOME/Android/Sdk"
fi

if [ -z "$sdk_dir" ] || [ ! -d "$sdk_dir" ]; then
    echo "Android unit tests and debug build skipped: Android SDK directory was not found."
    echo "Set ANDROID_SDK_ROOT or ANDROID_HOME, or install the SDK at \$HOME/Android/Sdk."
    exit 0
fi

export ANDROID_SDK_ROOT="$sdk_dir"
export ANDROID_HOME="$sdk_dir"
echo "Android SDK: $sdk_dir"

compile_sdk="$(awk -F= '/compileSdk/ { gsub(/[[:space:]]/, "", $2); print $2; exit }' app/build.gradle.kts)"
if [ -n "$compile_sdk" ] && [ ! -f "$sdk_dir/platforms/android-$compile_sdk/android.jar" ]; then
    echo "Android unit tests and debug build skipped: Android SDK platform android-$compile_sdk is not installed."
    echo "Installed platforms:"
    find "$sdk_dir/platforms" -maxdepth 1 -type d -name 'android-*' -printf '  %f\n' 2>/dev/null || true
    exit 0
fi

if [ ! -x "$sdk_dir/platform-tools/adb" ]; then
    echo "Android unit tests and debug build skipped: adb was not found at $sdk_dir/platform-tools/adb."
    exit 0
fi

if [ -x ./gradlew ]; then
    gradle_cmd="./gradlew"
elif command -v gradle >/dev/null 2>&1; then
    gradle_cmd="gradle"
else
    echo "Android unit tests and debug build skipped: gradle is not installed and no Gradle wrapper is present."
    exit 0
fi

$gradle_cmd :app:testDebugUnitTest
$gradle_cmd :app:assembleDebug
