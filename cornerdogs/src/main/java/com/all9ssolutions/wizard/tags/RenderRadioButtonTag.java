// Copyright (c) 2024 Joseph Burris, all9s Solutions LLC
// SPDX-License-Identifier: BSD-3-Clause

package com.all9ssolutions.wizard.tags;

import jakarta.servlet.jsp.JspException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class models a custom radio button tag. The code as it lives in a JSP feels bloated; nesting
 * it in a tag where the logic can be repeated without being repeated as a scriptlet reduces the
 * footprint.
 * 
 * @see com.all9ssolutions.wizard.tags.AbstractTag
 * @author Joseph Burris, all9s Solutions LLC
 */
public class RenderRadioButtonTag extends AbstractTag {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger("com.all9ssolutions.wizard.tags.RenderRadioButtonTag");
	public int doStartTag() throws JspException {
		StringBuilder buffer = new StringBuilder();
		buffer.append("<p>").append(escapeHtml(label)).append("</p>");
		buffer.append("<div style=\"display:block;\">");
		buffer.append("<label class=\"radio-inline\">");
		buffer.append("<input type=\"radio\" id=\"").append(escapeHtml(id)).append("\" name=\"").append(escapeHtml(name)).append("\" value=\"Y\"");
		if ("Y".equalsIgnoreCase(value)) {
			buffer.append(" checked");
		}// end if
		buffer.append("><span style=\"padding-left:1rem\">Yes</span>");
		buffer.append("</label>");
		buffer.append("<label class=\"radio-inline\">");
		buffer.append("<input type=\"radio\" id=\"").append(escapeHtml(id)).append("-no\" name=\"").append(escapeHtml(name)).append("\" value=\"N\"");
		if (!"Y".equalsIgnoreCase(value)) {
			buffer.append(" checked");
		}// end if
		buffer.append("><span style=\"padding-left:1rem\">No</span>");
		buffer.append("</label>");
		if (null != error) {
			buffer.append("<span class=\"error\">").append(escapeHtml(error)).append("</span>");
		}// end if
		buffer.append("</div>");
		try {
			pageContext.getOut().print(buffer.toString());
			pageContext.getOut().flush();
		} catch (Exception e) {
			logger.error("Unable to access the JspWriter to render radio button tag. Message is: " + e.getMessage(), e);
		}// end try/catch
		return EVAL_PAGE;
	}// end doStartTag
}// end class
