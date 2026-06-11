/*
 * Keith Framework
 * Copyright (c) 2026 Joseph Burris, all9s Solutions LLC
 *
 * Licensed under the MIT License. See LICENSE file in the project root.
 */
package com.all9ssolutions.keith.logging;

import static org.junit.Assert.assertTrue;

import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.Test;

/**
 * Tests formatting behavior for {@link LogStatementFormatter}.
 * 
 * @author Joseph Burris, all9s Solutions LLC
 */
public class LogStatementFormatterTest {

	@Test
	public void formatIncludesLevelSourceAndMessage() {
		LogRecord record = new LogRecord(Level.INFO, "hello");
		record.setSourceClassName("example.Source");
		record.setSourceMethodName("run");

		String formatted = new LogStatementFormatter().format(record);

		assertTrue(formatted.contains("[INFO|"));
		assertTrue(formatted.contains("|example.Source#run]: hello"));
	}

	@Test
	public void formatMessageAppliesParametersWhenMessageUsesPlaceholders() {
		LogRecord record = new LogRecord(Level.INFO, "Hello {0}");
		record.setParameters(new Object[] { "Keith" });

		assertTrue(new LogStatementFormatter().formatMessage(record).contains("Hello Keith"));
	}
}
