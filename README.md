# all9s Solutions Eclipse Tools

This workspace contains Eclipse plug-ins, features, and a local p2 update site for Java editor productivity tools from all9s Solutions.

## Projects

- `com.all9ssolutions.branding` - shared branding bundle used by the top-level feature.
- `com.all9ssolutions.ender` - adds configurable ending comments to Java methods and control blocks.
- `com.all9ssolutions.ender.feature` - installable Ender feature metadata.
- `com.all9ssolutions.lumberjack` - generates configurable Java logging statements.
- `com.all9ssolutions.lumberjack.feature` - installable Lumberjack feature metadata.
- `com.all9ssolutions.lumberjack.help` - Eclipse help content for Lumberjack.
- `com.all9ssolutions.feature` - top-level feature that includes the tool features and branding bundle.
- `com.all9ssolutions.update` - generated p2 update site.

## Requirements

- Eclipse IDE with PDE installed for development.
- Java 11 compatibility for the plug-in source and generated bundles.
- Eclipse Marketplace listings should reference the published p2 update-site URL and the feature IDs exposed by that update site.

## Local Install Test

1. Open Eclipse.
2. Select `Help > Install New Software...`.
3. Add the local update site: `file:/D:/Workspaces/projects/com.all9ssolutions.update/`.
4. Select the `all9s Solutions` category and install the available features.
5. Restart Eclipse when prompted.
6. Open a Java source file and verify the Ender and Lumberjack toolbar, editor context-menu, and keyboard-command entries.

## Feature IDs

- `com.all9ssolutions`
- `com.all9ssolutions.ender`
- `com.all9ssolutions.lumberjack`

## Release Checklist

- Regenerate `com.all9ssolutions.update` from the source plug-in and feature projects.
- Install from a clean Eclipse profile using the generated p2 update site.
- Confirm toolbar icons, editor context-menu icons, commands, preferences, and help content.
- Publish the p2 update site to a stable HTTPS location before submitting Eclipse Marketplace entries.
- Use the feature IDs above in Marketplace metadata.

## License

Licensed under the Eclipse Public License 2.0. See `LICENSE`.