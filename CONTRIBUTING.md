# Contributing

Thank you for helping improve the all9s Solutions Eclipse tools.

## Development Setup

1. Import the `com.all9ssolutions.*` projects into an Eclipse workspace with PDE installed.
2. Use Java 11 compatibility for source changes.
3. Run the plug-ins in an Eclipse runtime workbench for manual verification.
4. Regenerate the p2 update site after changes that affect bundles, features, icons, metadata, or help content.

## Pull Request Checklist

- Keep plug-in IDs, package names, feature IDs, and update-site metadata under `com.all9ssolutions.*`.
- Do not add generated `bin/` or `target/` output to source control.
- Update user-facing help or README content when behavior changes.
- Verify installation from the generated update site before release.