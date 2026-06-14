// Copyright (c) 2024 Joseph Burris, all9s Solutions LLC
// SPDX-License-Identifier: BSD-3-Clause

package com.all9ssolutions.cornerdogs.enums;

import com.all9ssolutions.wizard.tags.Common;

/**
 * This {@code enum} is used to store labels for reference.
 * 
 * @see com.all9ssolutions.wizard.tags.Common
 * @author Joseph Burris, all9s Solutions LLC
 */
public enum Where implements Common {
	EMPTY("", "..."), //
	HERE("HERE", "For Here"), //
	TOGO("TOGO", "To Go");

	private final String key;
	private final String value;

	/**
	 * {@code private} overloaded constructor instantiates this class
	 * 
	 * @param key   the {@code enum} key
	 * @param value the {@code enum} value
	 */
	private Where(String key, String value) {
		this.key = key;
		this.value = value;
	}// end constructor
	@Override
	public String getKey() {
		return key;
	}// end getKey
	@Override
	public String getValue() {
		return value;
	}// end getValue
}// end enum
