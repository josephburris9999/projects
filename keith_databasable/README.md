# Keith Databasable Plug-in

Keith Databasable is a Keith Framework plug-in that provides reusable database access helpers for automated Java processes.

## Purpose

This plug-in centralizes common database configuration, validation, warning logging, exception logging, connection creation, and cleanup behavior. Applications that need database access can use this plug-in alongside the core Keith framework instead of recreating those patterns.

## Key Features

* Database property model with required driver, URL, username, and password fields
* Validation support for applications that expose one or more database configurations
* Connection helper backed by `DriverManager`
* Cleanup helper for `Connection`, `PreparedStatement`, and `ResultSet`
* Recursive SQL warning and exception logging

## Requirements

* Java 1.8 or later
* Keith Framework on the classpath
* A JDBC driver for the target database on the application classpath

## Project Structure

```text
keith_databasable/
|-- src/            Source code
|-- src/test/java/  JUnit tests
|-- lib/            Optional JDBC driver dependencies
|-- README.md       Project documentation
`-- LICENSE         License information
```

## Testing

JUnit 4 tests are located under `src/test/java`. In Eclipse, run the project tests with the JUnit runner after the Keith Framework project is available in the workspace.

## License

Keith Databasable is released under the MIT License. See the `LICENSE` file for details.
