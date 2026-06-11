/*
 * Keith Framework Plug-in
 * Copyright (c) 2026 Joseph Burris, all9s Solutions LLC
 *
 * Licensed under the MIT License. See LICENSE file in the project root.
 */
package com.all9ssolutions.keith_steppable.core;

import com.all9ssolutions.keith.core.AbstractApplication;
import com.all9ssolutions.keith.properties.CustomProperties;

/**
 * This interface supports a steps concept useful in automated processes. The code contained herein is dependent on the Keith framework being available on the classpath.
 * 
 * @author Joseph Burris, all9s Solutions LLC
 */
public interface ISteppable {
	/**
	 * constant represents the required key for application.properties
	 */
	static final String STEP_KEY = "step";

	/**
	 * validates the {@link #STEP_KEY} has been added to the getProperties() method
	 * 
	 * @throws NullPointerException exception if the {@code STEP_KEY} is not available
	 */
	static void validate() {
		CustomProperties properties = AbstractApplication.getApplicationProperties();
		if (!properties.containsKey(STEP_KEY)) {
			throw new NullPointerException("The step key does not exist in getProperties().");
		}
	}

	/**
	 * reads the value of the {@link #STEP_KEY} as stored in application.properties
	 * 
	 * @return the value of the {@code STEP_KEY}
	 */
	static String readStep() {
		return AbstractApplication.getApplicationProperties().getProperty(STEP_KEY).trim();
	}

	/**
	 * writes a new value to the {@link #STEP_KEY} in application.properties
	 * 
	 * @param step new value to update in application.properties
	 */
	static void writeStep(String step) {
		CustomProperties properties = AbstractApplication.getApplicationProperties();
		properties.put(STEP_KEY, step);
		properties.write();
	}
}