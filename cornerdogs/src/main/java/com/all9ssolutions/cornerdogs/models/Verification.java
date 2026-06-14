// Copyright (c) 2024 Joseph Burris, all9s Solutions LLC
// SPDX-License-Identifier: BSD-3-Clause

package com.all9ssolutions.cornerdogs.models;

import java.util.Map;

/**
 * This class models the submit type from the verification JSP.
 * 
 * @see com.all9ssolutions.cornerdogs.models.AbstractModel
 * @author Joseph Burris, all9s Solutions LLC
 */
public class Verification extends AbstractModel {
	private static final long serialVersionUID = 1L;

	/**
	 * default constructor instantiates this class
	 */
	public Verification() {
		super();
	}// end constructor
	@Override
	public void setValues(Map<String, String[]> map) {
		setSubmit(getFirst(map, "submit"));
	}// end setValues
	@Override
	public String toString() {
		return "Verification [getUid()=" + getUid() + ", getErrors()=" + getErrors() + ", getSubmit()=" + getSubmit()
				+ ", toString()=" + super.toString() + "]";
	}// end toString
}// end class
