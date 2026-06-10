/*
 * Keith Framework
 * Copyright (c) 2026 Joseph Burris, all9s Solutions LLC
 *
 * Licensed under the MIT License. See LICENSE file in the project root.
 */
package com.all9ssolutions.keith.properties;

import java.io.Serializable;

/**
 * This interface requires the implementing class to support an action when the backing file is changed, and how often to check the backing file for changes.
 * <p>
 * <b>Concept contributed to and inspired by Dwayne T. Walker.</b>
 * </p>
 * 
 * @author Joseph Burris, all9s Solutions LLC
 */
interface IFileMonitor extends Serializable {

	/**
	 * invoked when the monitored file changes
	 * 
	 * @param path absolute path of the monitored file
	 */
	public void fileChanged(String path);

	/**
	 * set the time between checks for a monitored file
	 * 
	 * @param millis time in milliseconds between file checks
	 * @param path   absolute path of the monitored file
	 */
	public void setCheckPeriod(long millis, String path);
}
