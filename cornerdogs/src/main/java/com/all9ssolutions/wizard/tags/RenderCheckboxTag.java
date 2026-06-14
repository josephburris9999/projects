// Copyright (c) 2024 Joseph Burris, all9s Solutions LLC
// SPDX-License-Identifier: BSD-3-Clause

package com.all9ssolutions.wizard.tags;

import jakarta.servlet.jsp.JspException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class models a custom checkbox input tag. The code as it lives in a JSP feels bloated;
 * nesting it in a tag where the logic can be repeated without being repeated as a scriptlet reduces
 * the footprint.
 * 
 * @see com.all9ssolutions.wizard.tags.AbstractTag
 * @author Joseph Burris, all9s Solutions LLC
 */
public class RenderCheckboxTag extends AbstractTag {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger("com.all9ssolutions.wizard.tags.RenderCheckboxTag");
	public int doStartTag() throws JspException {
		StringBuilder buffer = new StringBuilder();
		buffer.append("<div>");
		buffer.append("<label class=\"checkbox-inline\">");
		buffer.append("<input type=\"checkbox\" id=\"").append(escapeHtml(id)).append("\" name=\"").append(escapeHtml(name))
				.append("\" value=\"Y\"");
		if ("Y".equalsIgnoreCase(value)) {
			buffer.append(" checked");
		} // end if
		buffer.append("><span style=\"padding-left:1rem\">").append(escapeHtml(label)).append("</span>");
		buffer.append("</label>");
		if (null != error) {
			buffer.append("<span class=\"error\">").append(escapeHtml(error)).append("</span>");
		} // end if
		buffer.append("</div>");
		try {
			pageContext.getOut().print(buffer.toString());
			pageContext.getOut().flush();
		} catch (Exception e) {
			logger.error("Unable to access the JspWriter to render checkbox tag. Message is: " + e.getMessage(), e);
		} // end try/catch
		return EVAL_PAGE;
	}// end doStartTag
}// end class
