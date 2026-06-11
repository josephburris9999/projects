/*
 * Keith Framework Plug-in
 * Copyright (c) 2026 Joseph Burris, all9s Solutions LLC
 *
 * Licensed under the MIT License. See LICENSE file in the project root.
 */
package com.all9ssolutions.keith_databasable.core;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.all9ssolutions.keith.core.AbstractApplication;

/**
 * Tests validation behavior for applications implementing {@link IDatabasable}.
 * 
 * @author Joseph Burris, all9s Solutions LLC
 */
public class IDatabasableTest {

	@Test(expected = ClassCastException.class)
	public void validateRejectsApplicationsThatDoNotImplementInterface() {
		IDatabasable.validate(new PlainApplication(new String[0]));
	}

	@Test(expected = NullPointerException.class)
	public void validateRejectsMissingProperties() {
		IDatabasable.validate(new DatabasableApplication(null));
	}

	@Test(expected = NullPointerException.class)
	public void validateRejectsNullEntries() {
		IDatabasable.validate(new DatabasableApplication(new DatabaseProperties[] { null }));
	}

	@Test(expected = RuntimeException.class)
	public void validateRejectsInvalidProperties() {
		IDatabasable.validate(new DatabasableApplication(new DatabaseProperties[] { new DatabaseProperties("invalid") }));
	}

	@Test(expected = RuntimeException.class)
	public void validateRejectsDuplicateIds() {
		IDatabasable.validate(new DatabasableApplication(new DatabaseProperties[] { validProperties("duplicate"), validProperties("duplicate") }));
	}

	@Test
	public void validateAcceptsValidUniqueProperties() {
		IDatabasable.validate(new DatabasableApplication(new DatabaseProperties[] { validProperties("primary"), validProperties("secondary") }));

		assertTrue(true);
	}

	private static DatabaseProperties validProperties(String id) {
		DatabaseProperties properties = new DatabaseProperties(id);
		properties.setDriver("java.lang.String");
		properties.setUrl("jdbc:test");
		properties.setPassword("secret");
		properties.setUsername("user");
		return properties;
	}

	public static class PlainApplication extends AbstractApplication {
		private static final long serialVersionUID = 1L;

		public PlainApplication(String[] args) {
			super(args);
		}

		@Override
		protected Map<String, String> getProperties() {
			return defaultProperties();
		}

		@Override
		protected void run() throws Exception {
		}
	}

	public static class DatabasableApplication extends AbstractApplication implements IDatabasable {
		private static final long serialVersionUID = 1L;
		private static DatabaseProperties[] properties;

		public DatabasableApplication(DatabaseProperties[] properties) {
			super(new String[0]);
			DatabasableApplication.properties = properties;
		}

		@Override
		protected Map<String, String> getProperties() {
			return defaultProperties();
		}

		@Override
		protected void run() throws Exception {
		}

		@Override
		public DatabaseProperties[] getDatabaseProperties() {
			return properties;
		}
	}

	private static Map<String, String> defaultProperties() {
		Map<String, String> properties = new HashMap<>();
		properties.put("check.period", "3000");
		return properties;
	}
}
