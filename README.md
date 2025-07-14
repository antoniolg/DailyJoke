# DailyJoke 📱

A simple Android application that displays daily jokes fetched from an external API. Built with Kotlin and Jetpack Compose, showcasing modern Android development practices.

## Features

- 🎯 Fetches jokes from [JokeAPI](https://sv443.net/jokeapi/v2/)
- 📱 Clean Material3 UI with Jetpack Compose
- 🏗️ MVVM architecture with reactive state management
- 🔄 Proper loading states and error handling
- 🎨 Modern Android development practices

## Technologies Used

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose with Material3
- **Architecture**: MVVM (Model-View-ViewModel)
- **Network**: Retrofit for API calls
- **State Management**: ViewModel with StateFlow
- **Build System**: Gradle with Kotlin DSL

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
└── ui/theme/                   # Compose theming
```

## Getting Started

### Prerequisites

- Android Studio Arctic Fox or later
- JDK 11 or later
- Android SDK 24 or later

### Installation

1. Clone the repository:
```bash
git clone https://github.com/antoniolg/DailyJoke.git
```

2. Open the project in Android Studio

3. Build and run the project:
```bash
./gradlew assembleDebug
```

## Development

### Building the Project

```bash
# Build the project
./gradlew build

# Clean build artifacts
./gradlew clean

# Run tests
./gradlew test

# Run lint checks
./gradlew lint
```

### Running Tests

```bash
# Run unit tests
./gradlew testDebugUnitTest

# Run instrumentation tests (requires device/emulator)
./gradlew connectedDebugAndroidTest
```

## API Reference

This app uses the [JokeAPI](https://sv443.net/jokeapi/v2/) service:
- **Base URL**: `https://sv443.net/jokeapi/v2/`
- **Endpoint**: `/joke/Any?type=twopart`
- **No authentication required**

## Architecture

The app follows the MVVM (Model-View-ViewModel) pattern with clean architecture principles:

- **Data Layer**: Handles API calls and data persistence
- **Domain Layer**: Contains business logic and use cases
- **Presentation Layer**: Manages UI state and user interactions

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- [JokeAPI](https://sv443.net/jokeapi/v2/) for providing the jokes
- Android Jetpack Compose team for the amazing UI toolkit
- The Android development community for continuous inspiration