// Copyright (c) 2024 Joseph Burris, all9s Solutions LLC
// SPDX-License-Identifier: BSD-3-Clause

package com.all9ssolutions.cornerdogs.models;

import java.util.regex.Pattern;

/**
 * This {@code interface} contains constants for the application.
 *
 * @author Joseph Burris, all9s Solutions LLC 
 */
public interface IConstants {
	/** {@code java.sql.Date} pattern */
	public static final Pattern SQL_DATE_PATTERN = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
	/** {@code java.util.Date} pattern */
	public static final Pattern UTIL_DATE_PATTERN = Pattern.compile("\\d{2}/\\d{2}/\\d{4}");
	/** number pattern */
	public static final Pattern NUMBER_PATTERN = Pattern.compile("\\d{1,7}");

	/** {@code java.text.SimpleDateFormat} for {@code java.sql.Date}s */
	public static final String SDF_SQL_DATE = "yyyy-MM-dd";
	/** {@code java.text.SimpleDateFormat} for {@code java.util.Date}s */
	public static final String SDF_UTIL_DATE = "MM/dd/yyyy";
	/** {@code java.text.SimpleDateFormat} for {@code java.sql.Timestamp}s */
	public static final String SDF_TIMESTAMP = "M/d/yy H:m a";

	/** the operating system specific new line character */
	public static final String NEW_LINE = System.getProperty("line.separator");

	/** the customer session key constant */
	public static final String CUSTOMER_SESSION_KEY = "s_customer";
	/** the breadcrumb session key constant */
	public static final String BREADCRUMB_SESSION_KEY = "s_breadcrumb";

	/**
	 * This class contains pseudo-constants holding values set by initialization parameters in the
	 * deployment descriptor.
	 * 
	 */
	static class InitParameters {
		/** the file path for the log directory */
		public static String LOG_DIRECTORY;
		/** the hot dog price */
		public static String HOT_DOG_PRICE;
		/** the tax rate */
		public static String TAX_RATE;

		/**
		 * builds a {@code String} with the values of the initialization parameters found in the deployment
		 * descriptor
		 * 
		 * @return a {@code String} of initialization parameters
		 */
		public static String print() {
			return new StringBuilder("LOG_DIRECTORY=").append(LOG_DIRECTORY).append(NEW_LINE) //
					.append("HOT_DOG_PRICE=").append(HOT_DOG_PRICE).append(NEW_LINE) //
					.append("TAX_RATE=").append(TAX_RATE).toString();
		}// end print
	}// end nested class
}// end class
