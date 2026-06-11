/*
 * Keith Framework
 * Copyright (c) 2026 Joseph Burris, all9s Solutions LLC
 *
 * Licensed under the MIT License. See LICENSE file in the project root.
 */
package com.all9ssolutions.keith.logging;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.all9ssolutions.keith.core.AbstractApplication;
import com.all9ssolutions.keith.properties.CustomProperties;

/**
 * This class manages loading external logging properties for program execution. During execution, this class listens for changes to the properties and reloads based on the changes.
 * 
 * @author Joseph Burris, all9s Solutions LLC
 * 
 * @see com.all9ssolutions.keith.properties.CustomProperties
 */
public class LogProperties extends CustomProperties {
	private static final long serialVersionUID = 1L;
	/**
	 * data structure holds the qualified package paths in the application
	 */
	private Set<String> packages = new TreeSet<>();

	/**
	 * overloaded constructor instantiates this class
	 * 
	 * @param filename name of the external properties file to create and monitor
	 */
	public LogProperties(String filename) {
		super(filename);
		String applicationName = AbstractApplication.getApplicationName();
		Map<String, String> map = new HashMap<>();
		map.put("handlers", "java.util.logging.ConsoleHandler,java.util.logging.FileHandler");
		map.put(".level", "INFO");
		map.put("java.util.logging.ConsoleHandler.level", "ALL");
		map.put("java.util.logging.ConsoleHandler.formatter", "com.all9ssolutions.keith.logging.LogStatementFormatter");
		map.put("java.util.logging.FileHandler.level", "ALL");
		map.put("java.util.logging.FileHandler.formatter", "com.all9ssolutions.keith.logging.LogStatementFormatter");
		map.put("java.util.logging.FileHandler.limit", "200000");
		map.put("java.util.logging.FileHandler.count", "100");
		try {
			Path path = file.toPath().getParent().resolve("logs");
			Files.createDirectories(path);
			map.put("java.util.logging.FileHandler.pattern", path.resolve(applicationName + "%u.%g.log").toString());
		} catch (Exception e) {
			throw new IllegalStateException("Unable to initialize log file path.", e);
		}
		map.put("java.util.logging.FileHandler.append", "true");
		setPackages(applicationName);
		for (String pkg : packages) {
			map.put(pkg + ".level", "INFO");
		}
		synchronize(map);
		try (BufferedInputStream stream = new BufferedInputStream(new FileInputStream(file))) {
			LogManager.getLogManager().readConfiguration(stream);
		} catch (IOException e) {
			throw new IllegalStateException("Unable to load logging configuration " + file, e);
		}
	}

	/**
	 * change the log level for all loggers during runtime for one check period
	 * 
	 * @param level {@link Level} to temporarily set all loggers to
	 */
	public void setLoggingLevel(Level level) {
		LogManager manager = LogManager.getLogManager();
		Map<String, Level> before = new HashMap<>();
		String name;
		Enumeration<String> loggerNames = manager.getLoggerNames();
		while (loggerNames.hasMoreElements()) {
			name = loggerNames.nextElement();
			if (!"".equals(name)) {
				before.put(name, manager.getLogger(name).getLevel());
				Logger.getLogger(name).setLevel(level);
			}
		}
		ExecutorService service = Executors.newSingleThreadExecutor();
		service.execute(new Runnable() {
			public void run() {
				try {
					TimeUnit.MILLISECONDS.sleep(getCheckPeriod());
					for (Map.Entry<String, Level> entry : before.entrySet()) {
						Logger.getLogger(entry.getKey()).setLevel(entry.getValue());
					}
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		});
		service.shutdown();
	}

	/**
	 * finds all package paths in the application in both development and compiled structures
	 * 
	 * @param applicationName name of the application
	 */
	private void setPackages(String applicationName) {
		try {
			Path location = AbstractApplication.getLocationPath();
			if (AbstractApplication.isJar()) {
				try (JarFile jar = new JarFile(location.toFile())) {
					JarEntry entry;
					String path;
					Enumeration<JarEntry> entries = jar.entries();
					while (entries.hasMoreElements()) {
						entry = entries.nextElement();
						if (entry.getName().endsWith(".class")) {
							path = entry.toString();
							packages.add(toPackageName(path.substring(0, path.lastIndexOf('/')), '/'));
						}
					}
				}
			} else {
				File root = location.toFile();
				Path rootPath = location;
				File[] children = root.listFiles();
				if (null != children) {
					for (int i = 0, j = children.length; i < j; i++) {
						if (!children[i].getName().equals(applicationName)) {
							getPackagePaths(children[i], rootPath);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}

	/**
	 * recursive method finds all package paths in the application in development structure
	 * 
	 * @param file the file to search for {@code .class} files
	 * @param root root directory used to calculate package-relative paths
	 * @throws IOException exception if the canonical path does not exist (won't happen)
	 */
	private void getPackagePaths(File file, Path root) throws IOException {
		if (file.isDirectory()) {
			File[] children = file.listFiles();
			if (null != children) {
				for (int i = 0, j = children.length; i < j; i++) {
					getPackagePaths(children[i], root);
				}
			}
		} else {
			if (file.getName().endsWith(".class")) {
				Path packagePath = root.relativize(file.getParentFile().toPath());
				packages.add(toPackageName(packagePath));
			}
		}
	}

	/**
	 * converts a class-file directory path into a package name
	 * 
	 * @param path      directory path containing compiled classes
	 * @param separator path separator used by the path
	 * @return package name for logger configuration
	 */
	private String toPackageName(String path, char separator) {
		while (path.length() > 0 && path.charAt(0) == separator) {
			path = path.substring(1);
		}
		return path.replace(separator, '.');
	}

	/**
	 * converts a class-file directory path into a package name
	 * 
	 * @param path directory path containing compiled classes
	 * @return package name for logger configuration
	 */
	private String toPackageName(Path path) {
		StringBuilder builder = new StringBuilder();
		for (Path part : path) {
			if (builder.length() > 0) {
				builder.append('.');
			}
			builder.append(part.toString());
		}
		return builder.toString();
	}
}
