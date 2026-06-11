/*
 * Keith Framework Plug-in
 * Copyright (c) 2026 Joseph Burris, all9s Solutions LLC
 *
 * Licensed under the MIT License. See LICENSE file in the project root.
 */
package com.all9ssolutions.keith_databasable.core;

import com.all9ssolutions.keith.core.AbstractApplication;

/**
 * This interface supports configuring a database for access in automated processes. The code contained herein is dependent on the Keith framework being available on the classpath.
 * 
 * @author Joseph Burris, all9s Solutions LLC
 */
public interface IDatabasable {
	/**
	 * constant represents the required property key for the qualified database driver class
	 */
	static final String DATABASE_DRIVER = "database.driver";
	/**
	 * constant represents the required property key for the database URL
	 */
	static final String DATABASE_URL = "database.url";
	/**
	 * constant represents the required property key for the database password
	 */
	static final String DATABASE_PASSWORD = "database.password";
	/**
	 * constant represents the required property key for the database user name
	 */
	static final String DATABASE_USERNAME = "database.username";

	/**
	 * provides a route for programmers to supply models for database access in the form of {@link DatabaseProperties}
	 * 
	 * @return an array of {@code DatabaseProperties} objects
	 */
	DatabaseProperties[] getDatabaseProperties();

	/**
	 * validates at least one {@link DatabaseProperties} object exists and the database ID's are unique
	 * 
	 * @param application the class implementing this interface
	 * @throws NullPointerException exception if there are no {@code DatabaseProperties} objects
	 * @throws RuntimeException     exception if a {@code DatabaseProperties} object is fully formed
	 */
	static void validate(AbstractApplication application) {
		if (!(application instanceof IDatabasable)) {
			throw new ClassCastException("The parameter class must implement IDatabasable.");
		}
		DatabaseProperties[] properties = ((IDatabasable) application).getDatabaseProperties();
		if (null == properties || properties.length == 0) {
			throw new NullPointerException("There must be at least one DatabaseProperties defined in getDatabaseProperties().");
		}
		for (DatabaseProperties model : properties) {
			if (null == model) {
				throw new NullPointerException("There may not be any null entries in getDatabaseProperties().");
			}
			if (!model.isValid()) {
				throw new RuntimeException("The DatabaseProperties(" + model.getId() + ") must have values for all fields.");
			}
			int count = 0;
			for (DatabaseProperties that : properties) {
				if (that.equals(model)) {
					count++;
				}
			}
			if (count > 1) {
				throw new RuntimeException("Duplicate database ID found. Check getDatabaseProperties() and insure each model has a unique ID.");
			}
			model.initialize();
		}
	}
}
