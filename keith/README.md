# Keith Framework

Keith Framework is an extensible Java application framework designed to accelerate development through reusable architecture, standardized components, and plug-in based enhancements.

The framework provides a stable foundation for Java applications while allowing additional functionality to be added through independently developed extensions.

## Purpose

Keith Framework was created to reduce repeated development effort across enterprise software projects. Instead of rebuilding common foundational behavior for each application, the framework centralizes shared patterns and provides a consistent structure for future development.

## Key Features

* Extensible Java architecture
* Plug-in based enhancement model
* Reusable framework components
* Standardized application structure
* Reduced duplication across projects
* Improved maintainability and consistency
* No third-party runtime library dependencies
* JUnit test coverage for core startup, property synchronization, and logging utilities

## Requirements

* Java 1.8 or later
* JUnit 4 for running automated tests

Keith Framework does not require Maven or Gradle. The framework runtime does not require external dependencies; JUnit 4 is used only for automated tests.

## Project Structure

```text
KeithFramework/
|-- src/            Source code
|-- src/test/java/  JUnit tests
|-- bin/            Compiled classes
|-- lib/            Optional library/output location
|-- README.md       Project documentation
`-- LICENSE         License information
```

## Plug-in Model

Keith Framework is designed to support additional functionality through plug-ins. The core framework provides the foundation, while plug-ins demonstrate how new capabilities can be added without modifying the framework itself.

This separation allows the framework to remain stable while supporting future expansion.

## Demonstration Plug-ins

The Keith Framework portfolio includes separate demonstration plug-ins showing how the framework can be extended with additional functionality.

Each plug-in is intended to demonstrate a specific extension concept and should be reviewed alongside the core framework.

## Building the Project

Keith Framework can be compiled using standard Java tools or through an IDE such as Eclipse.

Since the project has no external dependencies, it can be built directly from source using Java 1.8 or later.

## Testing

JUnit 4 tests are located under `src/test/java`. In Eclipse, run the tests with the JUnit runner after the project has been refreshed and cleaned.

The tests cover application startup behavior, application property synchronization, elapsed-time formatting, and log statement formatting.

## License

Keith Framework is released under the MIT License. See the `LICENSE` file for details.

## Author

Joseph Burris, all9s Solutions LLC
