# Expert Implementation Questions

## Q1: Should we use Hilt dependency injection for repository injection in the core:data module?
**Default if unknown:** Yes (Hilt is already available in libs.versions.toml and follows Android best practices)

## Q2: Should the core:ui module expose a single public API through a main Theme.kt file that re-exports all components?
**Default if unknown:** Yes (creates cleaner API surface and easier imports for app module)

## Q3: Should we create build.gradle.kts convention plugins in buildSrc/ to standardize module configurations?
**Default if unknown:** No (adds complexity; use direct module configurations initially)

## Q4: Should the Message and User data models in core:data be made @Parcelize for navigation arguments?
**Default if unknown:** Yes (maintains compatibility with existing navigation using Safe Args)

## Q5: Should we move the drawable resources (R.drawable references) from data models to the app module?
**Default if unknown:** Yes (keeps resources in app module while data models reference them through interfaces)