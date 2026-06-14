# Corner Dogs

Corner Dogs is a small Jakarta EE wizard demo for a fictional hot dog counter. It was migrated from an older Java EE / Java 8 Dynamic Web Project to Jakarta EE for Tomcat 11.

Author: Joseph Burris, all9s Solutions LLC.

## Runtime

- Java 17 or newer
- Apache Tomcat 11
- Jakarta Servlet 6.1 / JSP 4.0
- Log4j 2.26.0

## Build

The project can still be imported into Eclipse as a Dynamic Web Project, and it now includes a Maven build for repeatable command-line packaging.

```powershell
mvn clean package
```

The generated WAR is `target/cornerdogs.war`.

If Maven is not installed on your PATH, this workspace also supports a project-local Maven install:

```powershell
.\.tools\apache-maven-3.9.16\bin\mvn.cmd clean package
```

Maven owns dependency versions. Any local jars under `src/main/webapp/WEB-INF/lib` are ignored by Git and should be treated as Eclipse/local fallback copies, not source-controlled dependencies.

## Eclipse

1. Import the project from `D:\Workspaces\projects\cornerdogs`.
2. Use a Java 17+ JDK.
3. Target the project to Tomcat 11.
4. If Eclipse prompts for Maven project configuration, allow it to update the classpath.

## Demo Flow

The app collects a customer name, an order, and a final verification step. Submitting the order is intentionally demo-only: no order is persisted, emailed, or sent to another system. The server logs a demo submission event, and the confirmation page tells the user that the submit was not persisted.

## Notes

- Dependencies are declared in `pom.xml`.
- Bootstrap and Bootstrap Icons are loaded from version-pinned jsDelivr CDN URLs in the shared JSP fragments.
- `src/main/resources/log4j2.properties` configures application logging.
- `WEB-INF/web.xml` uses the Jakarta EE 6.1 deployment descriptor schema.

## License

BSD 3-Clause. See `LICENSE`.
