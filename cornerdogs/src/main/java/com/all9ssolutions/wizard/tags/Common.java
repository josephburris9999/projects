// Copyright (c) 2024 Joseph Burris, all9s Solutions LLC
// SPDX-License-Identifier: BSD-3-Clause

package com.all9ssolutions.wizard.tags;

/**
 * This {@code interface} defines common methods to be implemented by {@code enum}s. While
 * unnecessary in the usual scenarios, this type is required for use in the
 * {@link com.all9ssolutions.wizard.tags.RenderSelectTag}.
 *
 * @author Joseph Burris, all9s Solutions LLC 
 */
public interface Common {

	/**
	 * returns the key
	 * 
	 * @return the {@code enum} key
	 */
	String getKey();

	/**
	 * returns the value
	 * 
	 * @return the {@code enum} value
	 */
	String getValue();
}// end interface
