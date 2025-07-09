# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Repository Overview

Jetchat is a sample Android chat application built with Jetpack Compose, demonstrating modern Android development practices with Material Design 3 theming and dynamic color support.

## Build and Development Commands

### Building the Project
```bash
# Clean and build
./gradlew clean build

# Build Debug APK
./gradlew assembleDebug

# Build Release APK
./gradlew assembleRelease
```

### Running Tests
```bash
# Run all unit tests
./gradlew test

# Run instrumentation tests on connected device
./gradlew connectedAndroidTest

# Run specific test variant
./gradlew testDebugUnitTest
./gradlew connectedDebugAndroidTest
```

### Code Quality
```bash
# Run lint checks
./gradlew lint
./gradlew lintDebug

# Apply code formatting (Spotless with ktlint)
./gradlew --init-script buildscripts/init.gradle.kts spotlessApply

# Check code formatting
./gradlew --init-script buildscripts/init.gradle.kts spotlessCheck
```

### Running the App
```bash
# Install on connected device
./gradlew installDebug

# Uninstall
./gradlew uninstallDebug
```

## Architecture Overview

### Technology Stack
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose with Material 3
- **Architecture Pattern**: MVVM with hybrid Fragment/Compose approach
- **Navigation**: AndroidX Navigation Component
- **State Management**: ViewModel + LiveData + Compose State
- **Build System**: Gradle with Kotlin DSL

### Project Structure
```
app/src/main/java/com/example/compose/jetchat/
├── components/        # Reusable UI components (AppBars, Drawers, FAB animations)
├── conversation/      # Chat screen implementation
│   ├── Conversation.kt         # Main conversation UI
│   ├── ConversationFragment.kt # Fragment container
│   ├── UserInput.kt           # Message input with emoji selector
│   └── MessageFormatter.kt    # Message formatting logic
├── profile/          # User profile feature
├── data/            # Data layer (currently mock data)
├── theme/           # Material 3 theming with dynamic color
├── widget/          # Home screen widget using Glance API
├── NavActivity.kt   # Main activity with navigation setup
└── MainViewModel.kt # App-level state management
```

### Key Architectural Patterns

1. **Hybrid Architecture**: Fragments serve as containers for Compose UI content, enabling navigation component integration while using modern Compose UI.

2. **State Management**: Combines traditional ViewModel/LiveData for Fragment communication with Compose state for UI state management. See `ConversationUiState` for screen-level state and local composable state like `currentInputSelector` in UserInput.

3. **Navigation**: Uses XML navigation graph with AndroidX Navigation Component. Profile data is passed between fragments using Safe Args.

4. **Edge-to-Edge UI**: Implements immersive UI with proper insets handling using `Modifier.navigationBarsPadding().imePadding()` for synchronized IME transitions.

5. **Testing Strategy**: Comprehensive UI tests using Compose testing APIs, including tests for navigation, user input, and dark mode transitions.

### Important Implementation Details

- **Back Button Handling**: Custom back navigation when emoji selector is shown (see UserInput.kt)
- **Focus Management**: Coordinated keyboard/emoji panel transitions using FocusRequester
- **Animations**: From simple AnimatedVisibility to complex choreographed FAB transitions
- **Nested Scrolling**: Interop between View CoordinatorLayout and Compose using rememberNestedScrollInteropConnection()
- **Mock Data**: No real backend - uses fake data from the data package

### Development Notes

- Always use Android Studio's latest stable version
- The project uses version catalogs (libs.versions.toml) for dependency management
- Spotless formatting must be run with the init script: `--init-script buildscripts/init.gradle.kts`
- Release builds require signing configuration (see app/build.gradle.kts)
- Widget implementation uses Glance API for Compose-based app widgets