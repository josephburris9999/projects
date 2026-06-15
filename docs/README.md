# all9s Solutions Eclipse Tools

This workspace contains Eclipse plug-ins, features, help bundles, and a local p2 update site for Java editor productivity tools from all9s Solutions.

## Projects

- `com.all9ssolutions.branding` - shared branding bundle used by the top-level feature.
- `com.all9ssolutions.ender` - adds configurable ending comments to Java methods and control blocks.
- `com.all9ssolutions.ender.help` - Eclipse help content for Ender.
- `com.all9ssolutions.ender.feature` - installable Ender feature metadata.
- `com.all9ssolutions.lumberjack` - generates configurable Java logging statements.
- `com.all9ssolutions.lumberjack.help` - Eclipse help content for Lumberjack.
- `com.all9ssolutions.lumberjack.feature` - installable Lumberjack feature metadata.
- `com.all9ssolutions.feature` - complete suite feature that includes Ender, Lumberjack, and branding.
- `com.all9ssolutions.update` - generated p2 update site.

## Requirements

- Eclipse IDE with PDE installed for development.
- Java 11 compatibility for the plug-in source and generated bundles.
- Current local validation target: Eclipse 2026-06 / Platform 4.40 with Java 21 running the IDE and JavaSE-11 bundle compatibility.
- Before Marketplace submission, validate and publish the minimum supported Eclipse release in the listing.

## Local Install Test

1. Open Eclipse.
2. Select `Help > Install New Software...`.
3. Add the local update site: `file:/D:/Workspaces/projects/com.all9ssolutions.update/`.
4. Select one of the `all9s Solutions` categories and install the desired feature.
5. Restart Eclipse when prompted.
6. Open a Java source file and verify the Ender and Lumberjack toolbar, editor context-menu, keyboard-command, preferences, icons, and help entries.

## Feature IDs

- `com.all9ssolutions` - complete suite.
- `com.all9ssolutions.ender` - Ender.
- `com.all9ssolutions.lumberjack` - Lumberjack.

## Repository Hygiene

Generated `bin/`, `target/`, `.metadata/`, and local analysis folders are ignored. Eclipse PDE metadata files (`.project`, `.classpath`, and `.settings/`) are intentionally kept because this repository is meant to be imported directly into Eclipse PDE.

## Release Checklist

- Regenerate `com.all9ssolutions.update` from the source plug-in and feature projects.
- Install from a clean Eclipse profile using the generated p2 update site.
- Confirm toolbar icons, editor context-menu icons, commands, preferences, feature branding, and Eclipse help content.
- Publish the p2 update site to a stable HTTPS location before submitting Eclipse Marketplace entries.
- Use the feature IDs above in Marketplace metadata.
- Publish screenshots, before/after examples, release notes, an issue tracker, and source repository links before public launch.

## License

Licensed under the Eclipse Public License 2.0. See `LICENSE`.