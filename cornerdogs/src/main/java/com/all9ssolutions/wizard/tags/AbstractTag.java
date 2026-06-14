// Copyright (c) 2024 Joseph Burris, all9s Solutions LLC
// SPDX-License-Identifier: BSD-3-Clause

package com.all9ssolutions.wizard.tags;

import jakarta.servlet.jsp.tagext.TagSupport;

/**
 * This class models the common attributes for the wizard tags.
 * 
 * @see jakarta.servlet.jsp.tagext.TagSupport
 * @author Joseph Burris, all9s Solutions LLC
 */
public class AbstractTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	protected String label;
	protected String name;
	protected String value;
	protected String error;

	/**
	 * default constructor instantiates this class
	 */
	public AbstractTag() {
		super();
	}// end constructor

	/**
	 * escapes a value for safe use in generated HTML
	 * 
	 * @param value the value to escape
	 * @return the escaped value
	 */
	public static String escapeHtml(Object value) {
		if (null == value) {
			return "";
		}// end if
		String text = String.valueOf(value);
		StringBuilder builder = new StringBuilder(text.length());
		for (int i = 0, j = text.length(); i < j; i++) {
			char ch = text.charAt(i);
			switch (ch) {
			case '&':
				builder.append("&amp;");
				break;
			case '<':
				builder.append("&lt;");
				break;
			case '>':
				builder.append("&gt;");
				break;
			case '"':
				builder.append("&quot;");
				break;
			case '\'':
				builder.append("&#x27;");
				break;
			default:
				builder.append(ch);
				break;
			}// end switch
		}// end for
		return builder.toString();
	}// end escapeHtml

	/**
	 * sets the label
	 * 
	 * @param label the label to setff
	 */
	public void setLabel(String label) {
		this.label = label;
	}// end setLabel

	/**
	 * sets the name
	 * 
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}// end setName

	/**
	 * sets the value
	 * 
	 * @param value the value to set
	 */
	public void setValue(Object value) {
		if (null == value) {
			value = "";
		}// end if
		this.value = String.valueOf(value);
	}// end setValue

	/**
	 * sets the error
	 * 
	 * @param error the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}// end setError
}// end class
