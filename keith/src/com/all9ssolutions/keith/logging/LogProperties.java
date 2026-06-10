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
import java.nio.file.Files;
import java.nio.file.Paths;
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
			String path = file.getCanonicalPath();
			path = path.substring(0, path.lastIndexOf(File.separator) + 1) + "logs";
			Files.createDirectories(Paths.get(path));
			map.put("java.util.logging.FileHandler.pattern", path + File.separator + applicationName + "%u.%g.log");
		} catch (Exception e) {
			e.printStackTrace(System.err);
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
			e.printStackTrace(System.err);
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
			String location = AbstractApplication.getLocation();
			if (AbstractApplication.isJar()) {
				try (JarFile jar = new JarFile(location)) {
					JarEntry entry;
					String path;
					Enumeration<JarEntry> entries = jar.entries();
					while (entries.hasMoreElements()) {
						entry = entries.nextElement();
						if (entry.getName().endsWith(".class")) {
							path = entry.toString();
							packages.add(path.substring(0, path.lastIndexOf(File.separator)).replaceAll(File.separator, "."));
						}
					}
				}
			} else {
				int len = location.length();
				File[] files = new File(location).listFiles();
				for (int i = 0, j = files.length; i < j; i++) {
					if (!files[i].getName().equals(applicationName)) {
						getPackagePaths(files[i], len);
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
	 * @param len  length of the parent directory for sub-stringing the package path
	 * @throws IOException exception if the canonical path does not exist (won't happen)
	 */
	private void getPackagePaths(File file, int len) throws IOException {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0, j = files.length; i < j; i++) {
				getPackagePaths(files[i], len);
			}
		} else {
			if (file.getName().endsWith(".class")) {
				packages.add(file.getParentFile().getCanonicalPath().substring(len).replaceAll(File.separator, "."));
			}
		}
	}
}
