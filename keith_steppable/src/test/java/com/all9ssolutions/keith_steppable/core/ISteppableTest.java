/*
 * Keith Framework Plug-in
 * Copyright (c) 2026 Joseph Burris, all9s Solutions LLC
 *
 * Licensed under the MIT License. See LICENSE file in the project root.
 */
package com.all9ssolutions.keith_steppable.core;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.all9ssolutions.keith.core.AbstractApplication;

/**
 * Tests validation and persisted step behavior for {@link ISteppable}.
 * 
 * @author Joseph Burris, all9s Solutions LLC
 */
public class ISteppableTest {

	@Test
	public void validatePassesWhenStepPropertyExists() {
		TestApplication.withStep("start");

		ISteppable.validate();
	}

	@Test(expected = NullPointerException.class)
	public void validateRejectsMissingStepProperty() {
		TestApplication.withoutStep();

		ISteppable.validate();
	}

	@Test
	public void readStepReturnsTrimmedStepValue() {
		TestApplication.withStep("  queued  ");

		assertEquals("queued", ISteppable.readStep());
	}

	@Test
	public void writeStepUpdatesApplicationProperties() {
		TestApplication.withStep("start");

		ISteppable.writeStep("done");

		assertEquals("done", ISteppable.readStep());
	}

	public static class TestApplication extends AbstractApplication {
		private static final long serialVersionUID = 1L;
		private static Map<String, String> configuredProperties;

		private TestApplication() {
			super(new String[0]);
		}

		static TestApplication withStep(String step) {
			configuredProperties = defaultProperties();
			configuredProperties.put(ISteppable.STEP_KEY, step);
			TestApplication application = new TestApplication();
			AbstractApplication.getApplicationProperties().put(ISteppable.STEP_KEY, step);
			AbstractApplication.getApplicationProperties().write();
			return application;
		}

		static TestApplication withoutStep() {
			configuredProperties = defaultProperties();
			TestApplication application = new TestApplication();
			AbstractApplication.getApplicationProperties().remove(ISteppable.STEP_KEY);
			AbstractApplication.getApplicationProperties().write();
			return application;
		}

		@Override
		protected Map<String, String> getProperties() {
			return configuredProperties;
		}

		@Override
		protected void run() throws Exception {
		}

		private static Map<String, String> defaultProperties() {
			Map<String, String> properties = new HashMap<>();
			properties.put("check.period", "3000");
			return properties;
		}
	}
}
