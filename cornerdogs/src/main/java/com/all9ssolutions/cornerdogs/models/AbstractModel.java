// Copyright (c) 2024 Joseph Burris, all9s Solutions LLC
// SPDX-License-Identifier: BSD-3-Clause

package com.all9ssolutions.cornerdogs.models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Super-class for models in the application.
 *
 * @author Joseph Burris, all9s Solutions LLC 
 */
public abstract class AbstractModel implements IConstants, Serializable {
	private static final long serialVersionUID = 1L;
	private long uid;
	private Map<String, String> errors;
	private String submit;

	/**
	 * constructor is {@code protected} to limit instantiation of this class to sub-classes
	 */
	protected AbstractModel() {
		super();
		this.errors = new HashMap<>();
	}// end constructor

	/**
	 * validates the length of a variable value
	 * 
	 * @param errorKey error key for the variable
	 * @param value    value to test
	 * @param label    label to set to the error message
	 * @param length   expected maximum length of the variable value
	 * @return {@code true} if validation passes
	 */
	protected boolean validateLength(String errorKey, String value, String label, int length) {
		if (null != value && !"".equals(value) && value.trim().length() > length) {
			errors.put(errorKey, MessageFormat.format("{0} exceeds the allowed length.", label));
			return false;
		}// end if
		return true;
	}// end validateLength

	/**
	 * validates a {@code String} variable value
	 * 
	 * @param errorKey error key for the variable
	 * @param value    value to test
	 * @param label    label to set to the error message
	 * @param length   expected maximum length of the variable value
	 * @return {@code true} if validation passes
	 */
	protected boolean validateString(String errorKey, String value, String label, int length) {
		if (null == value || "".equals(value.trim())) {
			errors.put(errorKey, MessageFormat.format("{0} is required.", label));
			return false;
		}// end if
		if (!validateLength(errorKey, value, label, length)) {
			return false;
		}// end if
		return true;
	}// end validateString

	/**
	 * validates a {@code Date} variable value
	 * 
	 * @param errorKey error key for the variable
	 * @param value    value to test
	 * @param label    label to set to the error message
	 * @param when     whether the date should be past or future
	 * @return {@code true} if validation passes
	 */
	protected boolean validateDate(String errorKey, String value, String label, String when) {
		if (!validateString(errorKey, value, label, 10)) {
			return false;
		}// end if
		if (!SQL_DATE_PATTERN.matcher(value).matches() && !UTIL_DATE_PATTERN.matcher(value).matches()) {
			errors.put(errorKey, MessageFormat.format("{0} must be in {1} format.", label, SDF_UTIL_DATE));
			return false;
		}// end if
		try {
			Date dateEntry = new SimpleDateFormat(getPattern(value)).parse(value);
			Date now = new Date(System.currentTimeMillis());
			if ("past".equals(when) && dateEntry.after(now)) {
				errors.put(errorKey, MessageFormat.format("{0} must not be a current or future date.", label));
				return false;
			} else if ("future".equals(when) && dateEntry.before(now)) {
				errors.put(errorKey, MessageFormat.format("{0} must not be a current or past date.", label));
				return false;
			}// end if/else
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}// end try/catch
		return true;
	}// end validateDate

	/**
	 * validates a {@code Number} variable value
	 * 
	 * @param errorKey error key for the variable
	 * @param value    value to test
	 * @param label    label to set to the error message
	 * @param length   expected maximum length of the variable value
	 * @return {@code true} if validation passes
	 */
	protected boolean validateNumber(String errorKey, String value, String label, int length) {
		if (!validateString(errorKey, value, label, length)) {
			return false;
		}// end if
		if (!NUMBER_PATTERN.matcher(value).matches()) {
			errors.put(errorKey, MessageFormat.format("{0} must only be digits.", label));
			return false;
		}// end if
		return true;
	}// end validateNumber

	/**
	 * validates at least one value in an {@code array} is set to {@code true}
	 * 
	 * @param errorKey error key for variable
	 * @param label    label to set to the error message
	 * @param values   array of values to test
	 * @return {@code true} if validation passes
	 */
	protected boolean validateMulti(String errorKey, String label, String... values) {
		int count = 0;
		for (String value : values) {
			if (null != value && "Y".equals(value.trim())) {
				count++;
			}// end if
		}// end for
		if (count == 0) {
			errors.put(errorKey, MessageFormat.format("At least one {0} must be selected.", label));
			return false;
		}// end if
		return true;
	}// end validateMulti

	/**
	 * clears the errors from a previous submission
	 */
	public void clearErrors() {
		errors.clear();
	}// end clearErrors

	/**
	 * returns an error message from the map
	 * 
	 * @param errorKey the key for the error message in the map
	 * @return the error message, or empty {@code String} if none is found
	 */
	public String getError(String errorKey) {
		return errors.containsKey(errorKey) ? errors.get(errorKey) : "";
	}// end getError

	/**
	 * parses a {@code String} to a date
	 * 
	 * @param date the date to parse
	 * @return the parsed {@code java.util.Date}
	 */
	protected Date parseDate(String date) {
		try {
			return new SimpleDateFormat(getPattern(date)).parse(date);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}// end try/catch
	}// end parseDate

	/**
	 * parses a {@code String} to a timestamp
	 * 
	 * @param timestamp the timestamp to parse
	 * @return the parsed {@code java.sql.Timestamp}
	 */
	protected Timestamp parseTimestamp(String timestamp) {
		try {
			return new Timestamp(new SimpleDateFormat(getPattern(timestamp)).parse(timestamp).getTime());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}// end try/catch
	}// end parseTimestamp

	/**
	 * returns the first value from a request parameter map
	 * 
	 * @param map the request parameter map
	 * @param key the parameter key
	 * @return the first parameter value, or an empty {@code String}
	 */
	protected String getFirst(Map<String, String[]> map, String key) {
		if (null == map || null == map.get(key) || map.get(key).length == 0 || null == map.get(key)[0]) {
			return "";
		}// end if
		return map.get(key)[0];
	}// end getFirst

	/**
	 * returns the correct date pattern to parse/format
	 * 
	 * @param value the {@code String} value of the date.
	 */
	private String getPattern(String value) {
		String pattern = "";
		if (SQL_DATE_PATTERN.matcher(value).matches()) {
			pattern = SDF_SQL_DATE;
		} else if (UTIL_DATE_PATTERN.matcher(value).matches()) {
			pattern = SDF_UTIL_DATE;
		}// end if/else
		return pattern;
	}// end getPattern

	/**
	 * formats a date as a {@code String}
	 * 
	 * @param date    the {@code java.util.Date} to format
	 * @param pattern the {@code String} value of the pattern to format with
	 * @return a formatted {@code String}
	 */
	protected String getFormattedDate(Date date, String pattern) {
		return (null != date) ? new SimpleDateFormat(pattern).format(date) : "";
	}// end getFormattedDate

	/**
	 * returns 'Yes' or 'No' for the parameter
	 * 
	 * @param abbr the letter Y or N
	 * @return either Yes or No depending on the parameter
	 */
	protected String getYesOrNo(String abbr) {
		return "Y".equals(abbr) ? "Yes" : "No";
	}// end getYesOrNo

	/**
	 * required method to set class variables from the parameter map
	 * <p>
	 * <b>insure a submit parameter is set in the implementing classes</b>
	 * </p>
	 * 
	 * @param map the request parameter map from a page submission
	 */
	public abstract void setValues(Map<String, String[]> map);

	/*
	 * @see Object#hashCode
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (uid ^ (uid >>> 32));
		return result;
	}// end hashCode

	/*
	 * @see Object#equals(Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}// end if
		if (!(obj instanceof AbstractModel)) {
			return false;
		}// end if
		AbstractModel other = (AbstractModel) obj;
		if (uid != other.uid) {
			return false;
		}// end if
		return true;
	}// end equals

	/**
	 * returns the unique id for the model
	 * 
	 * @return the uid
	 */
	public long getUid() {
		return uid;
	}// end getUid

	/**
	 * sets the unique id for the model
	 * 
	 * @param uid the uid to set
	 */
	public void setUid(String uid) {
		if (validateNumber("uidError", uid, "Unique ID", 7)) {
			this.uid = Long.parseLong(uid);
		}// end if
	}// end setUid

	/**
	 * returns the error map
	 * 
	 * @return the errors
	 */
	public Map<String, String> getErrors() {
		return errors;
	}// end getErrors

	/**
	 * returns the submit value
	 * 
	 * @return the submit
	 */
	public String getSubmit() {
		return submit;
	}// end getSubmit

	/**
	 * sets the submit value
	 * 
	 * @param submit the submit to set
	 */
	public void setSubmit(String submit) {
		this.submit = submit;
	}// end setSubmit
}// end class
