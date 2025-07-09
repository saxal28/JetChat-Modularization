# Context Findings

## Current Architecture Analysis

### Build System
- **Gradle**: Uses Kotlin DSL with version catalogs (libs.versions.toml)
- **Current Structure**: Single-module app with `include(":app")` in settings.gradle.kts
- **Target SDK**: 33, Min SDK: 21, Compile SDK: 35
- **Kotlin Version**: 2.1.10 with Compose plugin

### Dependency Management
- **Version Catalogs**: Already uses `gradle/libs.versions.toml` for centralized dependency management
- **Compose BOM**: Uses `androidx.compose.bom` version 2025.02.00 for consistent Compose versions
- **No Modularization**: Currently all code is in the single `:app` module

### Code Organization Analysis

#### Current Package Structure
```
com.example.compose.jetchat/
├── components/        # Reusable UI components (6 files)
├── conversation/      # Chat feature (6 files)
├── data/             # Data layer (1 file - FakeData.kt)
├── profile/          # Profile feature (4 files)
├── theme/            # Theming system (3 files)
├── widget/           # Home screen widget (5 files)
├── NavActivity.kt    # Main activity
├── MainViewModel.kt  # App-level ViewModel
└── UiExtras.kt       # UI utilities
```

## Files to Move to Core:Data Module

### Data Models and Sources
1. **`data/FakeData.kt`** → `core/data/datasource/FakeDataSource.kt`
   - Contains all mock data (messages, profiles, emojis)
   - Current imports: Only uses R.drawable resources

2. **`conversation/ConversationUiState.kt`** → Extract `Message` model to `core/data/model/Message.kt`
   - Contains core `Message` data class
   - UI state parts remain in conversation module

3. **`profile/ProfileViewModel.kt`** → Extract `ProfileScreenState` to `core/data/model/User.kt`
   - Contains user profile data model
   - ViewModel logic remains in profile module

### Repository Pattern Implementation
- **New**: `core/data/repository/MessageRepository.kt` interface
- **New**: `core/data/repository/UserRepository.kt` interface  
- **New**: `core/data/repository/impl/` implementation classes

## Files to Move to Core:UI Module

### Theme System (Complete Migration)
1. **`theme/Color.kt`** → `core/ui/theme/Color.kt`
   - 21 color definitions for light/dark themes
   - No external dependencies

2. **`theme/Themes.kt`** → `core/ui/theme/Theme.kt`
   - JetchatTheme composable with Material 3 theming
   - Dynamic color support for Android 12+

3. **`theme/Typography.kt`** → `core/ui/theme/Typography.kt`
   - Complete typography system with Google Fonts
   - Montserrat and Karla font families

### Reusable UI Components
1. **`components/JetchatIcon.kt`** → `core/ui/components/JetchatIcon.kt`
2. **`components/JetchatAppBar.kt`** → `core/ui/components/JetchatAppBar.kt`
3. **`components/JetchatScaffold.kt`** → `core/ui/components/JetchatScaffold.kt`
4. **`components/JetchatDrawer.kt`** → `core/ui/components/JetchatDrawer.kt`
5. **`components/AnimatingFabContent.kt`** → `core/ui/components/AnimatingFabContent.kt`
6. **`components/BaseLineHeightModifier.kt`** → `core/ui/modifiers/BaselineHeightModifier.kt`

### UI Utilities
1. **`UiExtras.kt`** → `core/ui/components/Dialogs.kt`
   - Contains FunctionalityNotAvailablePopup

### Potentially Reusable Components
1. **`conversation/JumpToBottom.kt`** → `core/ui/components/JumpToBottom.kt`
2. **`conversation/RecordButton.kt`** → `core/ui/components/RecordButton.kt`
3. **`conversation/MessageFormatter.kt`** → `core/ui/utils/MessageFormatter.kt`

### Widget Theme System
1. **`widget/theme/Theme.kt`** → `core/ui/widget/theme/Theme.kt`
2. **`widget/theme/Type.kt`** → `core/ui/widget/theme/Typography.kt`

## Dependency Analysis

### Core:Data Dependencies
```kotlin
// Essential for data layer
implementation(libs.kotlinx.coroutines.android)
implementation(libs.androidx.annotation)

// Future expansion ready
// implementation(libs.androidx.room.runtime)
// implementation(libs.androidx.room.ktx)
// implementation(libs.okhttp3)
// implementation(libs.kotlinx.serialization.json)
```

### Core:UI Dependencies
```kotlin
// All current Compose UI dependencies
implementation(libs.androidx.compose.bom)
implementation(libs.androidx.compose.runtime)
implementation(libs.androidx.compose.ui)
implementation(libs.androidx.compose.ui.graphics)
implementation(libs.androidx.compose.ui.tooling.preview)
implementation(libs.androidx.compose.ui.util)
implementation(libs.androidx.compose.ui.googlefonts)
implementation(libs.androidx.compose.material3)
implementation(libs.androidx.compose.foundation.layout)
implementation(libs.androidx.compose.material.iconsExtended)
implementation(libs.androidx.glance.appwidget)
implementation(libs.androidx.glance.material3)
debugImplementation(libs.androidx.compose.ui.tooling)
```

### App Module Dependencies (After Modularization)
```kotlin
// Core modules
implementation(project(":core:data"))
implementation(project(":core:ui"))

// Feature-specific dependencies
implementation(libs.androidx.activity.compose)
implementation(libs.androidx.lifecycle.viewModelCompose)
implementation(libs.androidx.lifecycle.runtime.compose)
implementation(libs.androidx.navigation.fragment)
implementation(libs.androidx.navigation.ui.ktx)
implementation(libs.androidx.appcompat)
implementation(libs.androidx.core.ktx)
implementation(libs.androidx.compose.runtime.livedata)
implementation(libs.androidx.compose.ui.viewbinding)
```

## Integration Points

### Current Dependencies Between Packages
1. **App → Components**: Uses all reusable components
2. **App → Theme**: Uses JetchatTheme in NavActivity
3. **Conversation → Data**: Uses FakeData for mock messages
4. **Profile → Data**: Uses FakeData for mock profiles
5. **Components → Theme**: Uses colors and typography
6. **Widget → Data**: Uses FakeData for widget content

### Post-Modularization Dependencies
1. **App → Core:UI**: All UI components and theming
2. **App → Core:Data**: All data access through repositories
3. **Core:UI → Core:Data**: UI components may need data models for previews
4. **Core:UI ↔ Core:Data**: Minimal coupling, only for shared models

## Technical Constraints

### Backward Compatibility Requirements
- Maintain existing package structure in app module where possible
- Preserve all public APIs and composable signatures
- Keep existing navigation and fragment structure intact
- Maintain current build configuration patterns

### Testing Impact
- **UI Tests**: Need to mock repository interfaces instead of direct data access
- **Component Tests**: Can test core:ui components in isolation
- **Integration Tests**: Need to set up module dependencies correctly

### Build Performance Considerations
- **Parallel Builds**: Core modules can be built independently
- **Incremental Builds**: Changes to core modules will trigger app module rebuild
- **CI/CD**: Need to update build scripts to handle multi-module setup

## Similar Features Analysis

### Existing Modularization Patterns
- **Google's Now in Android**: Uses core:data, core:ui, and feature modules
- **Compose Samples**: Other samples show similar modularization approaches
- **Android Architecture Samples**: Demonstrates repository pattern with core modules

### Best Practices Identified
1. **Dependency Injection**: Use Hilt for repository injection (already available in version catalog)
2. **API Design**: Create clear interfaces for data layer
3. **Resource Management**: Keep drawable resources in app module, share through core:ui
4. **Testing Strategy**: Mock repositories at module boundaries
5. **Build Configuration**: Use convention plugins for consistent module setup