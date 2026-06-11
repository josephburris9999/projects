# Keith Steppable Plug-in

Keith Steppable is a Keith Framework plug-in that adds a simple persisted step concept for automated Java processes.

## Purpose

This plug-in helps long-running or repeatable automated processes record and resume their current step through the Keith application properties system.

## Key Features

* Standard `step` property key
* Validation that the application exposes the step property
* Read helper for retrieving the current step
* Write helper for updating and persisting the current step

## Requirements

* Java 1.8 or later
* Keith Framework on the classpath

## Project Structure

```text
keith_steppable/
|-- src/            Source code
|-- src/test/java/  JUnit tests
|-- README.md       Project documentation
`-- LICENSE         License information
```

## Testing

JUnit 4 tests are located under `src/test/java`. In Eclipse, run the project tests with the JUnit runner after the Keith Framework project is available in the workspace.

## License

Keith Steppable is released under the MIT License. See the `LICENSE` file for details.
