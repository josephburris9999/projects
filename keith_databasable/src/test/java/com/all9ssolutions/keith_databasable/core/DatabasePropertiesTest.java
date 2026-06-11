/*
 * Keith Framework Plug-in
 * Copyright (c) 2026 Joseph Burris, all9s Solutions LLC
 *
 * Licensed under the MIT License. See LICENSE file in the project root.
 */
package com.all9ssolutions.keith_databasable.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import com.all9ssolutions.keith.core.AbstractApplication;

/**
 * Tests validation, equality, and string output behavior for {@link DatabaseProperties}.
 * 
 * @author Joseph Burris, all9s Solutions LLC
 */
public class DatabasePropertiesTest {

	@BeforeClass
	public static void initializeApplication() {
		new TestApplication(new String[0]);
	}

	@Test(expected = RuntimeException.class)
	public void constructorRejectsBlankId() {
		new DatabaseProperties(" ");
	}

	@Test
	public void isValidRequiresAllDatabaseFields() {
		DatabaseProperties properties = new DatabaseProperties("validity");

		assertFalse(properties.isValid());

		properties.setDriver("java.lang.String");
		properties.setUrl("jdbc:test");
		properties.setPassword("secret");
		properties.setUsername("user");

		assertTrue(properties.isValid());
	}

	@Test
	public void equalityUsesDatabaseId() {
		assertEquals(new DatabaseProperties("same"), new DatabaseProperties("same"));
		assertNotEquals(new DatabaseProperties("same"), new DatabaseProperties("different"));
	}

	@Test
	public void toStringMasksPassword() {
		DatabaseProperties properties = new DatabaseProperties("masked");
		properties.setDriver("java.lang.String");
		properties.setUrl("jdbc:test");
		properties.setPassword("secret");
		properties.setUsername("user");

		String text = properties.toString();

		assertTrue(text.contains("id=masked"));
		assertTrue(text.contains("driver=java.lang.String"));
		assertTrue(text.contains("url=jdbc:test"));
		assertTrue(text.contains("username=user"));
		assertTrue(text.contains("password=(password exists but is not printed)"));
		assertFalse(text.contains("secret"));
	}

	public static class TestApplication extends AbstractApplication {
		private static final long serialVersionUID = 1L;

		public TestApplication(String[] args) {
			super(args);
		}

		@Override
		protected Map<String, String> getProperties() {
			Map<String, String> properties = new HashMap<>();
			properties.put("check.period", "3000");
			return properties;
		}

		@Override
		protected void run() throws Exception {
		}
	}
}
