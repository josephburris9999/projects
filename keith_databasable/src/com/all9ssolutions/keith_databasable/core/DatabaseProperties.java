/*
 * Keith Framework Plug-in
 * Copyright (c) 2026 Joseph Burris, all9s Solutions LLC
 *
 * Licensed under the MIT License. See LICENSE file in the project root.
 */
package com.all9ssolutions.keith_databasable.core;

import static com.all9ssolutions.keith_databasable.core.IDatabasable.DATABASE_DRIVER;
import static com.all9ssolutions.keith_databasable.core.IDatabasable.DATABASE_PASSWORD;
import static com.all9ssolutions.keith_databasable.core.IDatabasable.DATABASE_URL;
import static com.all9ssolutions.keith_databasable.core.IDatabasable.DATABASE_USERNAME;

import java.util.Iterator;

import com.all9ssolutions.keith.properties.CustomProperties;

/**
 * This class manages loading and accessing external database properties for program execution. During execution, this class listens for changes to the properties and reloads based on the changes.
 * 
 * @author Joseph Burris, all9s Solutions LLC
 * 
 * @see com.all9ssolutions.keith.properties.CustomProperties
 */
public class DatabaseProperties extends CustomProperties {
	private static final long serialVersionUID = 1L;
	/**
	 * class variable is the unique ID for the database
	 */
	private final String id;

	/**
	 * overloaded constructor instantiates this class
	 * 
	 * @param id unique database ID
	 * @throws RuntimeException exception if the parameter is {@code null} or blank
	 */
	public DatabaseProperties(String id) {
		super(id + ".properties");
		if (null == id || "".equals(id.trim())) {
			throw new RuntimeException("DatabaseProperties must have a database ID.");
		}
		this.id = id;
	}

	/**
	 * checks this object passes {@link #isValid()} check, initializes the configured qualified driver class, and stores the property key/value pairs
	 * 
	 * @throws RuntimeException exception if the qualified driver class is not on the application classpath
	 */
	void initialize() {
		if (isValid()) {
			try {
				Class.forName(getDriver());
				write();
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * validates the database ID, qualified driver class, url, password, and user name are not {@code null} or blank
	 * 
	 * @return {@code true} if the values exist for the variables
	 */
	public boolean isValid() {
		return (null != id && !"".equals(id.trim())) //
				&& (null != getDriver() && !"".equals(getDriver().trim())) //
				&& (null != getUrl() && !"".equals(getUrl().trim())) //
				&& (null != getPassword() && !"".equals(getPassword().trim())) //
				&& (null != getUsername() && !"".equals(getUsername().trim()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("DatabaseProperties [");
		builder.append("id=").append(id);
		String key;
		Iterator<Object> iter = this.keySet().iterator();
		while (iter.hasNext()) {
			key = (String) iter.next();
			switch (key) {
			case DATABASE_DRIVER:
				builder.append(", driver=").append(getDriver());
				break;
			case DATABASE_URL:
				builder.append(", url=").append(getUrl());
				break;
			case DATABASE_PASSWORD:
				builder.append(", password=").append((null != getPassword() && !"".equals(getPassword()) ? "(password exists but is not printed)" : "(password is null or blank)"));
				break;
			case DATABASE_USERNAME:
				builder.append(", username=").append(getUsername());
				break;
			}
		}
		builder.append("]");
		return builder.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof DatabaseProperties)) {
			return false;
		}
		DatabaseProperties other = (DatabaseProperties) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	/**
	 * unique ID for the database
	 * 
	 * @return the unique database ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * retrieves the qualified database driver class value from the external properties file
	 * 
	 * @return the qualified database driver class value
	 */
	public String getDriver() {
		return getProperty(DATABASE_DRIVER);
	}

	/**
	 * sets the qualified database driver class value to the external properties file
	 * 
	 * @param driver the qualified database driver class value
	 */
	public void setDriver(String driver) {
		setProperty(DATABASE_DRIVER, driver);
	}

	/**
	 * retrieves the database URL value from the external properties file
	 * 
	 * @return the database URL value
	 */
	public String getUrl() {
		return getProperty(DATABASE_URL);
	}

	/**
	 * sets the database URL value to the external properties file
	 * 
	 * @param url the database URL value
	 */
	public void setUrl(String url) {
		setProperty(DATABASE_URL, url);
	}

	/**
	 * retrieves the database password value from the external properties file
	 * 
	 * @return the database password value
	 */
	public String getPassword() {
		return getProperty(DATABASE_PASSWORD);
	}

	/**
	 * sets the database password value to the external properties file
	 * 
	 * @param password the database password value
	 */
	public void setPassword(String password) {
		setProperty(DATABASE_PASSWORD, password);
	}

	/**
	 * retrieves the database user name value from the external properties file
	 * 
	 * @return the database user name value
	 */
	public String getUsername() {
		return getProperty(DATABASE_USERNAME);
	}

	/**
	 * sets the database user name value to the external properties file
	 * 
	 * @param username the database user name value
	 */
	public void setUsername(String username) {
		setProperty(DATABASE_USERNAME, username);
	}
}
