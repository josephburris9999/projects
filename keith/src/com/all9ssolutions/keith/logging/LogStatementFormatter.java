/*
 * Keith Framework
 * Copyright (c) 2026 Joseph Burris, all9s Solutions LLC
 *
 * Licensed under the MIT License. See LICENSE file in the project root.
 */
package com.all9ssolutions.keith.logging;

import java.io.Serializable;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * This class is the logging statement formatter for the Keith framework.
 * <p>
 * <b>Concept contributed to and inspired by Dwayne T. Walker.</b>
 * </p>
 * 
 * @author Joseph Burris, all9s Solutions LLC
 */
public class LogStatementFormatter extends Formatter implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String NEW_LINE = System.getProperty("line.separator");

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String format(LogRecord record) {
		String loggerName = record.getLoggerName();
		if (loggerName == null) {
			loggerName = "root";
		}
		StringBuilder output = new StringBuilder();
		output.append("[").append(record.getLevel()).append('|').append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date(record.getMillis()))).append('|');
		output.append(record.getSourceClassName()).append('#').append(record.getSourceMethodName()).append("]: ").append(formatMessage(record));
		if (record.getThrown() != null) {
			output.append(' ');
			Throwable thrown = record.getThrown();
			StackTraceElement[] trace = thrown.getStackTrace();
			for (int i = 0, j = trace.length; i < j; i++) {
				output.append(NEW_LINE).append("    at ").append(trace[i].getClassName()).append('#').append(trace[i].getMethodName()).append("(Line:").append(trace[i].getLineNumber()).append(')');
			}
			output.append(NEW_LINE);
		}
		output.append(NEW_LINE);
		return output.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized String formatMessage(LogRecord record) {
		String format = record.getMessage();
		ResourceBundle bundle = record.getResourceBundle();
		if (null != bundle) {
			try {
				format = bundle.getString(record.getMessage());
			} catch (MissingResourceException ex) {
				format = record.getMessage();
			}
		}
		try {
			Object[] params = record.getParameters();
			if (null != params && params.length > 0) {
				if (format.indexOf("{0") >= 0 || format.indexOf("{1") >= 0 || format.indexOf("{2") >= 0 || format.indexOf("{3") >= 0) {
					format = MessageFormat.format(format, params);
				}
			}
		} catch (Exception e) {
			return format;
		}
		return format;
	}
}
