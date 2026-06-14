// Copyright (c) 2024 Joseph Burris, all9s Solutions LLC
// SPDX-License-Identifier: BSD-3-Clause

package com.all9ssolutions.cornerdogs.models;

import java.util.LinkedHashMap;

/**
 * This class models the list of pages a customer has completed. The elements of this class are
 * displayed at the top of the JSP for customer navigation.
 * 
 * @see java.util.LinkedHashMap
 * @author Joseph Burris, all9s Solutions LLC
 */
public class Breadcrumb extends LinkedHashMap<String, String> {
	private static final long serialVersionUID = 1L;
	@Override
	public String put(String key, String value) {
		if (!this.containsKey(key)) {
			return super.put(key, value);
		}// end if
		return super.get(key);
	}// end put
}// end class
