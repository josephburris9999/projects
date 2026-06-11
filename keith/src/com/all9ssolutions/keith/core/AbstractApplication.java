/*
 * Keith Framework
 * Copyright (c) 2026 Joseph Burris, all9s Solutions LLC
 *
 * Licensed under the MIT License. See LICENSE file in the project root.
 */
package com.all9ssolutions.keith.core;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.all9ssolutions.keith.logging.LogProperties;
import com.all9ssolutions.keith.properties.CustomProperties;

/**
 * This class is the starting point for enabling the Keith framework. <b>Programmers should extend this class and add both an overloaded constructor and a main method to start a stand-alone Java application. Example:</b>
 *
 * <pre>
 * public class Main extends AbstractApplication {
 * 	private static Logger logger = Logger.getLogger("com.appname.Main");
 *
 * 	public Main(String[] args) {
 * 		super(args);
 * 	}
 *
 * 	public static void main(String[] args) {
 * 		new Main(args);
 * 	}
 * }
 * </pre>
 * <p>
 * <b>Note: The overloaded constructor will only execute the call to the super class during the application run. Other code must be in the {@link #preprocess()}, {@link #process()}, {@link #run()}, or {@link #postprocess()} methods.</b>
 * </p>
 * <p>
 * To include properties which may change during the application run, the programmer should add them to the {@link #getProperties()} method, overridden in the implementing class. <b>See the {@link #getProperties()} javadoc for more information.</b>
 * </p>
 *
 * @author Joseph Burris, all9s Solutions LLC
 */
public abstract class AbstractApplication implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String CLASS_NAME = "com.all9ssolutions.keith.core.AbstractApplication";
	private static Logger logger;
	private static String[] args;
	private static Class<?> clazz;
	private static String applicationName;
	private static CustomProperties properties;
	private static LogProperties logProperties;

	/**
	 * overloaded constructor instantiates this class
	 * 
	 * @param args the {@code String[]} of command line arguments
	 */
	public AbstractApplication(final String[] args) {
		super();
		AbstractApplication.args = args;
		clazz = this.getClass();
		try {
			preprocess();
		} catch (Exception e) {
			System.err.println("An exception has occurred while executing the preprocess method. Details must be viewed in the console. Message is: " + e.getMessage());
			e.printStackTrace(System.err);
			System.exit(1);
		}
		setApplicationName();
		properties = new CustomProperties("application.properties");
		properties.synchronize(getProperties());
		logProperties = new LogProperties("logging.properties");
		logger = Logger.getLogger(CLASS_NAME, "com.all9ssolutions.keith.logging.messages");
		long start = System.currentTimeMillis();
		ResourceBundle bundle = ResourceBundle.getBundle("com.all9ssolutions.keith.logging.messages");
		String method = "";
		try {
			method = "process";
			logger.log(Level.INFO, "executing", method);
			process();
			method = "run";
			logger.log(Level.INFO, "executing", method);
			run();
			method = "postprocess";
			logger.log(Level.INFO, "executing", method);
			postprocess();
		} catch (Exception e) {
			logger.logrb(Level.SEVERE, CLASS_NAME, "<init>", bundle, "executing.fail", method);
			logger.log(Level.SEVERE, CLASS_NAME, e);
		}
		logger.info(applicationName + " running time is " + asTime(System.currentTimeMillis() - start));
		logger.exiting(CLASS_NAME, "AbstractApplication");
	}

	// ------------------------- super-class methods -------------------------
	/**
	 * set application properties to be saved in an external location, available for manipulation by the administrator during application run
	 * 
	 * @return map of key/value pairs representing application properties
	 */
	protected abstract Map<String, String> getProperties();

	/**
	 * provided for the programmer to execute application setup logic prior to the Keith framework initializing
	 * 
	 * @throws Exception exception if the logic fails
	 */
	protected void preprocess() throws Exception {
	}

	/**
	 * provided for the programmer to execute application setup logic after the Keith framework is initialized, but before application logic executes
	 * 
	 * @throws Exception exception if the logic fails
	 */
	protected void process() throws Exception {
	}

	/**
	 * required for the programmer to override and is intended to provide application logic
	 * 
	 * @throws Exception exception if the logic fails
	 */
	protected abstract void run() throws Exception;

	/**
	 * provided for the programmer to execute application shutdown or clean up logic
	 * 
	 * @throws Exception exception if the logic fails
	 */
	protected void postprocess() throws Exception {
	}

	// ------------------------- property methods -------------------------
	/**
	 * returns command line arguments passed in on application start
	 * 
	 * @return the {@code String[]} of command line arguments
	 */
	public static String[] getArgs() {
		return args;
	}

	/**
	 * the name of the application as determined by the automated process name
	 * 
	 * @return the application name
	 */
	public static String getApplicationName() {
		return applicationName;
	}

	/**
	 * key/value pairs configured in the {@link #getProperties()} method
	 * 
	 * @return the application properties
	 */
	public static CustomProperties getApplicationProperties() {
		listProperties("Application", properties);
		return properties;
	}

	/**
	 * key/value pairs configured in {@link LogProperties}
	 * 
	 * @return the log configuration properties
	 */
	public static LogProperties getLoggingProperties() {
		listProperties("Logging", logProperties);
		return logProperties;
	}

	/**
	 * set the application name whether in development or compiled structure
	 */
	private final void setApplicationName() {
		try {
			Path location = getLocationPath();
			if (isJar()) {
				String filename = location.getFileName().toString();
				applicationName = filename.substring(0, filename.length() - 4);
			} else {
				applicationName = location.getParent().getFileName().toString();
			}
			System.out.println("APPLICATION NAME:" + applicationName);
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}

	/**
	 * utility for printing properties
	 * 
	 * @param label      the properties file label, i.e., application, logging, etc.
	 * @param properties {@code Properties} instance to print
	 */
	private static void listProperties(String label, Properties properties) {
		if (logger.isLoggable(Level.CONFIG)) {
			logger.config(label + " Properties:");
			String key;
			Iterator<Object> iter = properties.keySet().iterator();
			while (iter.hasNext()) {
				key = (String) iter.next();
				if (!key.toLowerCase().contains("password")) {
					logger.config(key + "=" + properties.getProperty(key));
				}
			}
		}
	}

	// ------------------------- utility methods -------------------------
	/**
	 * returns the location of the automated process in the host machine
	 * 
	 * @return absolute path of the application's location in the hosting operating system
	 * @throws UnsupportedEncodingException exception if UTF-8 is unavailable for decode
	 */
	public final static String getLocation() throws UnsupportedEncodingException {
		try {
			return getLocationPath().toString();
		} catch (URISyntaxException e) {
			return URLDecoder.decode(clazz.getProtectionDomain().getCodeSource().getLocation().getFile(), "UTF-8");
		}
	}

	/**
	 * returns the location of the automated process as a platform-native path
	 * 
	 * @return absolute path of the application's location in the hosting operating system
	 * @throws URISyntaxException exception if the code source location cannot be converted to a URI
	 */
	public final static Path getLocationPath() throws URISyntaxException {
		return Paths.get(clazz.getProtectionDomain().getCodeSource().getLocation().toURI());
	}

	/**
	 * returns whether the automated process is compiled in a JAR file
	 * 
	 * @return {@code true} if the implementing application is in development or compiled structure
	 * @throws URISyntaxException exception if the code source location cannot be converted to a URI
	 */
	public final static boolean isJar() throws URISyntaxException {
		return getLocationPath().toString().toLowerCase().endsWith(".jar");
	}

	/**
	 * returns a formatted time from the parameter {@code millis}
	 *
	 * @param millis {@code long} value of milliseconds to format
	 * @return {@code String} value of formatted time
	 */
	public static String asTime(long millis) {
		long h = (millis / (1000 * 60 * 60)) % 24;
		String result = (h > 0L) ? (h + ((h > 1L) ? " hours " : " hour ")) : "";
		long m = (millis / (1000 * 60)) % 60;
		result += (m > 0L) ? (m + ((m > 1L) ? " minutes " : " minute ")) : "";
		long s = (millis / 1000) % 60;
		result += (s > 0L) ? (s + ((s > 1L) ? " seconds " : " second ")) : "";
		millis = millis % 1000;
		result += millis + ((millis > 1) ? " milliseconds" : " millisecond");
		return result;
	}
}
