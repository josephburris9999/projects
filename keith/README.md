<<<<<<< HEAD
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
* No third-party library dependencies

## Requirements

* Java 1.8 or later

Keith Framework does not require Maven, Gradle, or external dependencies.

## Project Structure

```text
KeithFramework/
├── src/        Source code
├── bin/        Compiled classes
├── lib/        Optional library/output location
├── README.md   Project documentation
└── LICENSE     License information
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

## License

Keith Framework is released under the MIT License. See the `LICENSE` file for details.

## Author

Joseph Burris, all9s Solutions LLC
=======
# Portfolio Projects

This repository contains portfolio projects built with HTML5, CSS3, and Bootstrap 5.

## Files

- `index.html` - Root project index page.
- `resume/index.html` - Responsive resume page.
- `resume/styles.css` - Custom resume styles layered on top of Bootstrap.
- `resume/assets/` - Optional folder for images, a downloadable PDF, or other resume assets.

## Local Preview

Open `index.html` in a browser to preview the project index. The resume is available from the index page or directly at `resume/index.html`.

## GitHub Pages Deployment

1. Push this repository to GitHub.
2. Open the repository settings.
3. Go to **Pages**.
4. Set the source to **Deploy from a branch**.
5. Choose the `main` branch and the `/root` folder.
6. Save the settings.

After GitHub Pages finishes publishing, the project index will be available at the Pages URL shown in the repository settings. The resume will be available under `/resume/`.

## Next Steps

- Replace placeholder contact information.
- Add real experience, projects, skills, and education.
- Add a downloadable PDF version in the `resume/assets/` folder.
- Customize the colors and spacing in `resume/styles.css`.
>>>>>>> 8d71d48ebfd1abeb89591962e9720d37b6464c51
