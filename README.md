# Flows Timer App

A simple Android timer application built with Jetpack Compose that demonstrates the usage of Kotlin Flows, specifically StateFlow for state management.

https://github.com/user-attachments/assets/cee8eaf9-411c-4edd-bf38-b9844c2f6dd5

## Features

- Simple countdown timer functionality
- Real-time updates using StateFlow
- Clean MVVM architecture
- Material 3 Design implementation
- Haptic feedback on timer completion
- Visual feedback with customized Snackbar

## Technical Implementation

### StateFlow Usage

The app showcases the power of StateFlow for reactive state management:

Key benefits demonstrated:
- Thread-safe state updates
- Hot stream of data that maintains the latest state
- Lifecycle-aware state management
- Efficient UI updates

### Haptic Feedback

The app implements vibration feedback when the timer completes:
- Version-aware implementation supporting Android 8.0 and above
- Customizable vibration patterns
- Integrated with visual feedback (Snackbar)

### Architecture Components

- **ViewModel**: Manages timer logic and state
- **StateFlow**: Handles reactive state updates
- **Jetpack Compose**: Declarative UI with state observation
- **Material 3**: Modern Android design implementation
- **VibrationHelper**: Utility class for handling device vibrations

## Getting Started

1. Clone the repository
2. Open in Android Studio
3. Run on an emulator or physical device

## Requirements

- Android Studio Arctic Fox or newer
- Minimum SDK: 24
- Kotlin 1.8.0 or higher
- Device with vibration capability
- Required Permissions: `android.permission.VIBRATE`

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.
