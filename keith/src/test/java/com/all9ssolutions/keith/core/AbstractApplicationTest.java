/*
 * Keith Framework
 * Copyright (c) 2026 Joseph Burris, all9s Solutions LLC
 *
 * Licensed under the MIT License. See LICENSE file in the project root.
 */
package com.all9ssolutions.keith.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * Tests application startup and utility behavior for {@link AbstractApplication}.
 * 
 * @author Joseph Burris, all9s Solutions LLC
 */
public class AbstractApplicationTest {

	@Test
	public void asTimeFormatsMillisecondsOnly() {
		assertEquals("1 millisecond", AbstractApplication.asTime(1L));
		assertEquals("2 milliseconds", AbstractApplication.asTime(2L));
	}

	@Test
	public void asTimeFormatsCompoundDuration() {
		assertEquals("1 hour 1 minute 1 second 5 milliseconds", AbstractApplication.asTime(3661005L));
	}

	@Test
	public void applicationInitializesConfiguredProperties() {
		new TestApplication(new String[] { "one", "two" });

		assertEquals("one", AbstractApplication.getArgs()[0]);
		assertEquals("3000", AbstractApplication.getApplicationProperties().getProperty("check.period"));
		assertEquals("configured", AbstractApplication.getApplicationProperties().getProperty("custom.value"));
		assertTrue(TestApplication.ran);
	}

	public static class TestApplication extends AbstractApplication {
		private static final long serialVersionUID = 1L;
		private static boolean ran;

		public TestApplication(String[] args) {
			super(args);
		}

		@Override
		protected Map<String, String> getProperties() {
			Map<String, String> properties = new HashMap<>();
			properties.put("check.period", "3000");
			properties.put("custom.value", "configured");
			return properties;
		}

		@Override
		protected void run() throws Exception {
			ran = true;
		}
	}
}
