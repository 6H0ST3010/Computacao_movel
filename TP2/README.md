# Tutorial 2-WeatherApp

Course: LEIC\
Student(s): Rodrigo Amaral\
Date: 17/4/26\
Repository URL: [https://github.com/6H0ST3010/Computacao_movel/TP2](https://github.com/6H0ST3010/Computacao_movel/tree/main/TP2)
---
## 1. Introduction
The purpose of this assignment was to strengthen Kotlin programming skills and Android development knowledge through the implementation of multiple Kotlin exercises and the development of the Cool Weather Application described in the assignment specification. The project focused on learning advanced Kotlin concepts such as generics, higher-order functions, extension functions, operator overloading, and applying those concepts in practical scenarios.

Additionally, the Android application component required the creation of a weather application capable of retrieving real-time weather information from the Open-Meteo API and displaying it in a responsive Android user interface. The app had to support different screen sizes, themes, screen orientations, and multilingual resources.

The objective was to implement all requested functionalities exactly as specified in the tutorial while following Android development best practices and using AI-assisted tools responsibly.

## 2. System Overview
The solution consists of two major parts:

Kotlin Exercises

A set of standalone Kotlin implementations covering:

Event log processing with sealed classes and extension functions
Generic in-memory cache with higher-order functions
Configurable data processing pipeline using lambdas
2D vector mathematics library with operator overloading
Cool Weather Application

An Android weather application that:

Retrieves weather data from the Open-Meteo REST API
Displays current weather information for selected coordinates
Supports dynamic weather icons based on WMO weather codes
Changes theme automatically according to day/night conditions
Adapts layouts for portrait and landscape orientations
Supports multilingual UI strings
Preserves user-entered coordinates during configuration changes

Primary use cases include:

Viewing weather for default Lisbon coordinates
Updating weather for custom latitude/longitude
Rotating device without losing user input/state
Viewing adapted UI in light/dark themes

## 3. Architecture and Design
Architecture

The project follows a modular and organized design separating concerns between:

Data Models for weather API responses
UI Layer for Activities and XML layouts
Business Logic for weather retrieval and UI update logic

Architecture

The project follows a modular and organized design separating concerns between:

Data Models for weather API responses
UI Layer for Activities and XML layouts
Business Logic for weather retrieval and UI update logic

## 4. Implementation
Kotlin Exercises
Event Log Processing

Implemented:

Sealed class hierarchy for Event
filterByUser() extension function
totalSpent() extension function
Higher-order processEvents() function
Generic Cache

Implemented:

Generic Cache<K,V>
put, get, evict, size
getOrPut
transform
snapshot
Data Pipeline

Implemented:

Pipeline class with ordered stages
Lambda-based transformations
Pipeline builder DSL
Vec2 Library

Implemented:

Arithmetic operator overloading
Magnitude comparison
Dot product / normalization
Index operator overloading
Weather Application
Main Modules
WeatherData.kt
Contains data models for API response parsing
MainActivity.kt
Handles API calls, threading, and UI updates
Themes.xml
Defines day/night and orientation themes
Strings.xml
Multilingual support

## 5. Testing and Validation
Testing Strategy

Manual testing was performed throughout development.

Test Cases
Valid latitude/longitude input
Invalid coordinate handling
Theme switching between day/night
Portrait/landscape rotation
Multilanguage switching
Different weather codes from API

Edge Cases Tested
Extreme coordinates
Missing/invalid API response
Device rotation during API request

## 6. Usage Instructions
---
Requirements
Android Studio
Android Emulator or Physical Device
Internet Connection
Setup
Clone repository
Open project in Android Studio / AntiGravity
Sync Gradle dependencies
Build project
Run
Launch emulator/device
Run application
Enter latitude/longitude if desired
Press Update

# Autonomous Software Engineering Sections - only for [AC OK , AI OK] sections
## 7. Prompting Strategy
“Help fix Android theme not updating after orientation change”
“How to preserve EditText values during screen rotation in Android?”
“Explain how to map WMO weather codes to drawable resources”
## 8. Autonomous Agent Workflow
AI tools contributed to:
Planning code structure
Generating boilerplate Kotlin classes
Suggesting Android XML layouts
Debugging threading/theme issues
Explaining Android lifecycle/state preservation
Assisting with documentation formatting

Development workflow:
Read assignment requirements
Plan implementation
Prompt AI for isolated components
Integrate manually
Test and debug
Refine prompts if needed

## 9. Verification of AI - Generated Artifacts
All AI-generated code was manually reviewed and tested before integration.

## 10. Human vs AI Contribution
Human-Developed:
Project planning and interpretation of assignment requirements
Final integration of all modules
Manual debugging and testing
UI adjustments and layout refinements
Theme configuration corrections
State preservation logic

AI-Assisted:
Boilerplate Kotlin generation
Debugging suggestions
Android lifecycle explanations
Architecture recommendations
Documentation support

## 11. Ethical and Responsible Use
AI tools were used strictly as development assistants and learning aids.

Risks encountered:
Occasionally incorrect Android lifecycle advice
Some generated code not aligned with assignment requirements
Overly generic implementations requiring adaptation

Mitigations:
Manual validation of all outputs
Testing every generated artifact
Rejecting unsuitable AI suggestions

# Development Process
## 12. Version Control and Commit History
Git was used throughout development to maintain project history.
Commit strategy included:
Initial project setup
Kotlin exercises implementation
Weather API integration
Theme implementation
Rotation/state fixes
Final testing and cleanup

Commit history reflects incremental progress rather than a single final upload.

## 13. Difficulties and Lessons Learned
Main Challenges:
Implementing dynamic theme switching correctly
Preserving latitude/longitude values during screen rotation

## 14. Future Improvements
---
Potential enhancements include:
Refactor weather app to MVVM architecture
Add offline caching
Improve API error handling
Add loading indicators
Support automatic GPS location
Implement weather forecast for multiple days
Improve UI animations and transitions

## 15. AI Usage Disclosure ( Mandatory )
The following AI tools were used during development:
ChatGPT
Android debugging help
Documentation assistance
Architecture explanations

DeepSeek
Alternative debugging suggestions
Code comparison and validation

AntiGravity
AI-assisted Android development environment
Build/deploy workflow assistance
Guided code generation
