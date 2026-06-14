// Copyright (c) 2024 Joseph Burris, all9s Solutions LLC
// SPDX-License-Identifier: BSD-3-Clause

package com.all9ssolutions.wizard.tags;

import jakarta.servlet.jsp.JspException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class models a custom text input tag. The code, as it lives in a JSP, feels bloated; nesting
 * it in a tag where the logic can be repeated without being repeated as a scriptlet reduces the
 * footprint.
 * 
 * @see com.all9ssolutions.wizard.tags.AbstractTag
 * @author Joseph Burris, all9s Solutions LLC
 */
public class RenderTextInputTag extends AbstractTag {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger("com.all9ssolutions.wizard.tags.RenderTextInputTag");
	private String type;
	public int doStartTag() throws JspException {
		StringBuilder buffer = new StringBuilder();
		buffer.append("<p>").append(escapeHtml(label));
		buffer.append("<input type=\"").append(escapeHtml(type)).append("\" id=\"").append(escapeHtml(id))
				.append("\" name=\"").append(escapeHtml(name)).append("\" class=\"form-control\" value=\"")
				.append(escapeHtml(value)).append("\">");
		if (null != error) {
			buffer.append("<span class=\"error\">").append(escapeHtml(error)).append("</span>");
		}// end if
		buffer.append("</p>");
		try {
			pageContext.getOut().print(buffer.toString());
			pageContext.getOut().flush();
		} catch (Exception e) {
			logger.error("Unable to access the JspWriter to render text input tag. Message is: " + e.getMessage(), e);
		}// end try/catch
		return EVAL_PAGE;
	}// end doStartTag

	/**
	 * sets the type
	 * 
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}// end setType
}// end class
