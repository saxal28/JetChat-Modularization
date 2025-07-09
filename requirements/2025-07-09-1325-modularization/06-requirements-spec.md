# Requirements Specification: Jetchat App Modularization

## Problem Statement

The Jetchat Android app currently exists as a single monolithic module, making it difficult to:
- Reuse UI components across potential future features
- Maintain separation of concerns between data and UI layers
- Scale the codebase with multiple developers
- Test individual components in isolation
- Optimize build times through parallel module compilation

## Solution Overview

Modularize the Jetchat app by extracting shared functionality into two core modules:
- **`core:data`**: Contains all data models, repositories, and data sources
- **`core:ui`**: Contains all reusable UI components, theming, and design system

## Functional Requirements

### Core:Data Module Requirements
1. **Data Models**: Extract and centralize all data models
   - Move `Message` from `ConversationUiState.kt` to `core/data/model/Message.kt`
   - Move `ProfileScreenState` to `core/data/model/User.kt` (renamed for clarity)
   - Make models `@Parcelize` for navigation compatibility

2. **Repository Pattern**: Implement repository interfaces for data access
   - Create `MessageRepository` interface with fake implementation
   - Create `UserRepository` interface with fake implementation
   - Use Hilt dependency injection for repository provision

3. **Data Sources**: Centralize all data source logic
   - Refactor `FakeData.kt` to `FakeDataSource.kt`
   - Remove direct R.drawable references from data models
   - Maintain existing mock data functionality

### Core:UI Module Requirements
1. **Theme System**: Complete theming system migration
   - Move `theme/` package entirely to `core:ui/theme/`
   - Maintain Material 3 theming with dynamic color support
   - Preserve Google Fonts integration (Montserrat, Karla)

2. **Reusable Components**: Extract all shareable UI components
   - Move `components/` package to `core:ui/components/`
   - Include app bars, drawers, icons, scaffolds, and animations
   - Maintain existing component APIs and behavior

3. **Public API**: Create clean module API surface
   - Single main Theme.kt file that re-exports all components
   - Organized imports for easier consumption by app module
   - Maintain backward compatibility with existing usage

## Technical Requirements

### Module Structure
```
core:data/
├── src/main/java/com/example/compose/jetchat/core/data/
│   ├── model/
│   │   ├── Message.kt
│   │   └── User.kt
│   ├── repository/
│   │   ├── MessageRepository.kt
│   │   ├── UserRepository.kt
│   │   └── impl/
│   │       ├── FakeMessageRepository.kt
│   │       └── FakeUserRepository.kt
│   ├── datasource/
│   │   └── FakeDataSource.kt
│   └── di/
│       └── DataModule.kt
└── build.gradle.kts

core:ui/
├── src/main/java/com/example/compose/jetchat/core/ui/
│   ├── theme/
│   │   ├── Color.kt
│   │   ├── Theme.kt
│   │   └── Typography.kt
│   ├── components/
│   │   ├── JetchatAppBar.kt
│   │   ├── JetchatDrawer.kt
│   │   ├── JetchatIcon.kt
│   │   ├── JetchatScaffold.kt
│   │   ├── AnimatingFabContent.kt
│   │   ├── JumpToBottom.kt
│   │   ├── RecordButton.kt
│   │   └── Dialogs.kt
│   ├── modifiers/
│   │   └── BaselineHeightModifier.kt
│   ├── utils/
│   │   └── MessageFormatter.kt
│   └── widget/
│       └── theme/
│           ├── Theme.kt
│           └── Typography.kt
└── build.gradle.kts
```

### Build Configuration

#### settings.gradle.kts
```kotlin
include(":app")
include(":core:data")
include(":core:ui")
```

#### core:data/build.gradle.kts
```kotlin
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kapt)
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.annotation)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}
```

#### core:ui/build.gradle.kts
```kotlin
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose)
}

dependencies {
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    
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
}
```

#### app/build.gradle.kts (Updated)
```kotlin
dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:ui"))
    
    // Existing dependencies minus those moved to core modules
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.compose.runtime.livedata)
    implementation(libs.androidx.compose.ui.viewbinding)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}
```

### Dependency Injection Setup

#### core:data/di/DataModule.kt
```kotlin
@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    abstract fun bindMessageRepository(
        fakeMessageRepository: FakeMessageRepository
    ): MessageRepository
    
    @Binds
    abstract fun bindUserRepository(
        fakeUserRepository: FakeUserRepository
    ): UserRepository
}
```

### Resource Management
- Keep all drawable resources in app module
- Pass resource IDs through repository interfaces or dependency injection
- Data models reference resources through interfaces, not direct R.drawable calls

## Implementation Hints and Patterns

### Data Layer Migration
1. **Extract Models**: Move data classes to core:data, add @Parcelize annotation
2. **Create Repositories**: Define interfaces first, then implement with existing fake data
3. **Update ViewModels**: Inject repositories instead of direct data access
4. **Remove R.drawable**: Replace with resource provider interfaces

### UI Layer Migration
1. **Move Theme System**: Complete package migration with no API changes
2. **Extract Components**: Move reusable components maintaining existing signatures
3. **Create Public API**: Single Theme.kt file with re-exports
4. **Update Imports**: Change import statements throughout app module

### Testing Strategy
1. **Repository Testing**: Mock repository interfaces in UI tests
2. **Component Testing**: Test core:ui components in isolation
3. **Integration Testing**: Test app module with real core module dependencies

## Acceptance Criteria

### Core:Data Module
- [ ] All data models extracted and @Parcelize added
- [ ] Repository interfaces created with fake implementations
- [ ] Hilt dependency injection configured
- [ ] No direct R.drawable references in data models
- [ ] All existing fake data functionality preserved

### Core:UI Module
- [ ] Complete theme system migrated (colors, typography, themes)
- [ ] All reusable components extracted
- [ ] Public API created with re-exports
- [ ] Widget theming system included
- [ ] All existing component APIs maintained

### App Module
- [ ] Dependencies updated to use core modules
- [ ] All imports updated to use core module APIs
- [ ] ViewModels updated to use repository injection
- [ ] All existing functionality preserved
- [ ] Navigation and fragment structure unchanged

### Build System
- [ ] settings.gradle.kts updated with new modules
- [ ] Module build.gradle.kts files created
- [ ] Version catalog dependencies properly distributed
- [ ] Build succeeds without errors
- [ ] All tests pass

### Verification
- [ ] App launches successfully
- [ ] All screens render correctly
- [ ] Navigation works as before
- [ ] Theme switching works (light/dark)
- [ ] Widget functionality preserved
- [ ] No compilation errors or warnings

## Assumptions

1. **Backward Compatibility**: All existing public APIs will be maintained
2. **Resource Management**: Drawable resources will remain in app module
3. **Testing**: Existing test structure will be updated but not fundamentally changed
4. **Build Tools**: Current Gradle and tooling versions will be used
5. **Dependencies**: No new external dependencies will be introduced beyond what's already in version catalog
6. **Navigation**: Current Fragment-based navigation will be preserved
7. **Performance**: Module extraction should not negatively impact app performance
8. **Future Expansion**: Architecture supports future addition of feature modules

## Migration Priority
1. **Phase 1**: Create core:data module with repository pattern
2. **Phase 2**: Create core:ui module with theme system
3. **Phase 3**: Extract and move UI components
4. **Phase 4**: Update app module dependencies and imports
5. **Phase 5**: Testing and verification