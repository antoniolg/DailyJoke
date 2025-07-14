# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

DailyJoke is a simple Android application that displays daily jokes fetched from an external API. Built with Kotlin and Jetpack Compose, it showcases modern Android development practices with a clean, single-module architecture.

### App Features
- Fetches jokes from JokeAPI (https://sv443.net/jokeapi/v2/)
- Displays joke setup and punchline
- Clean, Material3-based UI with Jetpack Compose
- Reactive state management with ViewModel

## Key Technologies

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose with Material3
- **Architecture**: MVVM (Model-View-ViewModel)
- **Network**: Retrofit for API calls
- **State Management**: ViewModel with StateFlow/LiveData
- **API**: JokeAPI (https://sv443.net/jokeapi/v2/)
- **Build System**: Gradle with Kotlin DSL
- **Target SDK**: 36 (Android 14+)
- **Minimum SDK**: 24 (Android 7.0)

## Project Structure

```
app/src/main/java/io/devexpert/dailyjoke/
├── MainActivity.kt              # Main activity with Compose setup
├── data/                       # Data layer
│   ├── api/                    # API service interfaces
│   ├── model/                  # Data models
│   └── repository/             # Repository implementations
├── domain/                     # Business logic layer
│   ├── model/                  # Domain models
│   └── repository/             # Repository interfaces
├── presentation/               # UI layer
│   ├── viewmodel/              # ViewModels
│   └── screen/                 # Compose screens
└── ui/theme/                   # Compose theming (Color.kt, Theme.kt, Type.kt)
```

## Development Commands

### Building and Testing
```bash
# Build the project
./gradlew build

# Clean build artifacts
./gradlew clean

# Assemble debug APK
./gradlew assembleDebug

# Run unit tests
./gradlew test
./gradlew testDebugUnitTest

# Run instrumentation tests (requires device/emulator)
./gradlew connectedAndroidTest
./gradlew connectedDebugAndroidTest
```

### Code Quality
```bash
# Run lint checks
./gradlew lint

# Run lint and apply safe fixes
./gradlew lintFix

# Update lint baseline
./gradlew updateLintBaseline
```

### Installation and Deployment
```bash
# Install debug build on connected device
./gradlew installDebug

# Uninstall debug build
./gradlew uninstallDebug
```

## Architecture Notes

- **Single Activity**: Uses MainActivity with Compose for UI
- **MVVM Pattern**: Clean architecture with separated layers (data, domain, presentation)
- **Repository Pattern**: Abstracts data sources and provides clean API to ViewModels
- **Dependency Injection**: Manual DI or Hilt for managing dependencies
- **State Management**: ViewModels use StateFlow/LiveData for reactive UI updates
- **Theme System**: Material3 theming located in `ui/theme/` package
- **Compose Integration**: Full Compose setup with edge-to-edge support
- **Testing Setup**: Standard Android testing with JUnit (unit tests) and Espresso (instrumentation tests)

## API Integration

### JokeAPI Details
- **Base URL**: `https://sv443.net/jokeapi/v2/`
- **Endpoint**: `/joke/Any?type=twopart`
- **Response Format**: JSON with `setup` and `delivery` fields
- **No Authentication**: Free API, no API key required

### Network Layer
- **Retrofit**: HTTP client for API calls
- **Gson/Moshi**: JSON serialization/deserialization
- **OkHttp**: HTTP client with interceptors for logging
- **Coroutines**: Asynchronous network operations

## State Management Patterns

### ViewModel Structure
- ViewModels expose UI state via StateFlow/LiveData
- Handle user actions through public methods
- Coordinate with Repository for data operations
- Manage loading states and error handling

### UI State
- Sealed classes for different UI states (Loading, Success, Error)
- Reactive composition with state observation
- Proper lifecycle management with collectAsState()

### Error Handling
- Network errors handled at repository level
- User-friendly error messages in UI
- Retry mechanisms for failed requests

## Key Configuration

- **Namespace**: `io.devexpert.dailyjoke`
- **Application ID**: `io.devexpert.dailyjoke`
- **Java Version**: 11 (source and target compatibility)
- **Kotlin JVM Target**: 11
- **Version Catalog**: Dependencies managed via `gradle/libs.versions.toml`

## Testing Framework

- **Unit Tests**: JUnit 4, located in `app/src/test/`
- **Instrumentation Tests**: AndroidJUnit + Espresso, located in `app/src/androidTest/`
- **Compose Testing**: UI tests with `androidx.compose.ui.test.junit4`

## Build Configuration

The project uses Gradle Version Catalog for dependency management. Key plugins:
- `com.android.application`
- `org.jetbrains.kotlin.android`
- `org.jetbrains.kotlin.plugin.compose`

All dependencies are referenced through the version catalog (`libs.versions.toml`).

## Implementation Guidelines

### Adding New Features
1. Start with domain models and repository interfaces
2. Implement data layer (API service, data models, repository)
3. Create or update ViewModel with appropriate state management
4. Build Compose UI components following Material3 guidelines
5. Add comprehensive unit tests for business logic
6. Include UI tests for critical user flows

### Common Patterns
- Use sealed classes for UI state representation
- Implement proper error handling with user-friendly messages
- Follow single responsibility principle in composables
- Use dependency injection for testability
- Implement proper loading states and empty states

### Required Dependencies
When implementing the app, ensure these dependencies are added to `libs.versions.toml`:
- `retrofit2` and `retrofit2-converter-gson` for networking
- `androidx.lifecycle.viewmodel-compose` for ViewModel integration
- `androidx.lifecycle.runtime-compose` for lifecycle-aware state
- `kotlinx.coroutines.android` for coroutines support
- `okhttp3.logging-interceptor` for network debugging

### Network Configuration
- Add `INTERNET` permission to AndroidManifest.xml
- Configure network security config if needed
- Handle network connectivity changes appropriately
- Implement proper timeout configurations in OkHttp

### UI Implementation Notes
- Use `collectAsState()` for observing ViewModel state in Compose
- Implement proper preview composables for development
- Follow Material3 design system guidelines
- Use proper spacing and typography from the theme
- Handle different screen sizes and orientations