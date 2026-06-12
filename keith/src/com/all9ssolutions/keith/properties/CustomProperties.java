/*
 * Keith Framework
 * Copyright (c) 2026 Joseph Burris, all9s Solutions LLC
 *
 * Licensed under the MIT License. See LICENSE file in the project root.
 */
package com.all9ssolutions.keith.properties;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Properties;

import com.all9ssolutions.keith.core.AbstractApplication;

/**
 * This class manages loading external properties for program execution. During execution, this class listens for changes to the properties and reloads based on the changes.
 * <p>
 * <b>Concept contributed to and inspired by Dwayne T. Walker.</b>
 * </p>
 * 
 * @author Joseph Burris, all9s Solutions LLC
 */
public class CustomProperties extends Properties implements IFileMonitor {
	private static final long serialVersionUID = 1L;
	/**
	 * {@link File} instance reference to the external properties file
	 */
	protected File file;
	/**
	 * default check period in milliseconds
	 */
	protected long checkPeriod = 2000L;

	/**
	 * overloaded constructor instantiates this class
	 * 
	 * @param filename name of the external properties file to create and monitor
	 */
	public CustomProperties(String filename) {
		super();
		try {
			Path location = AbstractApplication.getLocationPath();
			if (AbstractApplication.isJar()) {
				location = location.getParent();
			}
			Path directory = location.resolve(AbstractApplication.getApplicationName()).resolve("resources");
			if (!Files.exists(directory)) {
				System.out.println("DIRECTORY:" + directory.toUri());
				Files.createDirectories(directory);
			}
			Path path = directory.resolve(filename);
			file = path.toFile();
			if (!Files.exists(path)) {
				System.out.println("FILE:" + path.toUri());
				Files.createFile(path);
			}
			load(file.getCanonicalPath());
		} catch (Exception e) {
			throw new IllegalStateException("Unable to initialize properties file " + filename, e);
		}
	}

	/**
	 * loads the elements of this class with properties from the configured external file
	 * 
	 * @param path absolute path of the monitored file
	 */
	private void load(String path) {
		try {
			try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
				super.load(reader);
			}
			setCheckPeriod(getCheckPeriod(), new File(path).toString());
		} catch (Exception e) {
			throw new IllegalStateException("Unable to load properties file " + path, e);
		}
	}

	/**
	 * synchronizes the map of property keys and values with those in the configured external file
	 * 
	 * @param map a {@code Map<String, String>} of property keys and values to synchronize
	 */
	public void synchronize(Map<String, String> map) {
		if (null == map) {
			return;
		}
		if (null == file) {
			throw new IllegalStateException("Properties file was not initialized.");
		}
		if (!map.containsKey("check.period")) {
			System.out.println("Configured check period does not exist for file " + file.getName() + ". Check period is " + (checkPeriod / 1000L) + " seconds.");
		}
		boolean change = false;
		for (Map.Entry<String, String> entry : map.entrySet()) {
			String key = entry.getKey();
			if (!containsKey(key)) {
				put(key, entry.getValue());
				change = true;
			}
		}
		if (change) {
			write();
		}
	}

	/**
	 * writes the map of property keys and values in the configured external file
	 */
	public synchronized void write() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			store(writer, null);
		} catch (Exception e) {
			throw new IllegalStateException("Unable to write properties file " + file, e);
		}
	}

	/**
	 * calls the load method when the configured external file has been changed
	 */
	@Override
	public void fileChanged(String path) {
		load(path);
	}

	/**
	 * returns the check period as stored in the configured external file
	 * 
	 * @return configured check period in milliseconds
	 */
	protected long getCheckPeriod() {
		String checkPeriod = getProperty("check.period");
		return (null == checkPeriod) ? this.checkPeriod : Long.parseLong(checkPeriod);
	}

	/**
	 * configures the a file monitor with the wait time for checks
	 */
	@Override
	public void setCheckPeriod(long millis, String path) {
		FileMonitor.getInstance().addFileMonitor(this, path, millis);
	}
}
