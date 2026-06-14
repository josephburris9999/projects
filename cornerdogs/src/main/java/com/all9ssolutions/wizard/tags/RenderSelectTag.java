// Copyright (c) 2024 Joseph Burris, all9s Solutions LLC
// SPDX-License-Identifier: BSD-3-Clause

package com.all9ssolutions.wizard.tags;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import jakarta.servlet.jsp.JspException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class models a custom select input tag. The code, as it lives in a JSP, feels bloated;
 * nesting it in a tag where the logic can be repeated without being repeated as a scriptlet reduces
 * the footprint.
 * 
 * @see com.all9ssolutions.wizard.tags.AbstractTag
 * @author Joseph Burris, all9s Solutions LLC
 */
public class RenderSelectTag extends AbstractTag {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger("com.all9ssolutions.wizard.tags.RenderSelectTag");
	private String qualifiedPath;
	public int doStartTag() throws JspException {
		StringBuilder buffer = new StringBuilder();
		buffer.append("<p>").append(escapeHtml(label));
		buffer.append("<select id=\"").append(escapeHtml(id)).append("\" name=\"").append(escapeHtml(name))
				.append("\" class=\"form-control\">");
		Object[] values = null;
		try {
			Class<?> clazz = Class.forName(qualifiedPath);
			if (null != clazz) {
				if (Arrays.asList(clazz.getInterfaces()).contains(Common.class)) {
					Method method = clazz.getMethod("values", (Class[]) null);
					values = (Object[]) method.invoke(clazz, (Object[]) null);
				}// end if
			}// end if
		} catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException
				| IllegalAccessException e) {
			logger.error("Cannot create the class for the qualified path: " + String.valueOf(qualifiedPath), e);
		}// end try/catch
		if (null == values) {
			throw new JspException("Cannot render select options for qualified path: " + String.valueOf(qualifiedPath));
		}// end if
		String key, value;
		for (int i = 0, j = values.length; i < j; i++) {
			key = ((Common) values[i]).getKey();
			value = ((Common) values[i]).getValue();
			buffer.append("<option value=\"").append(escapeHtml(key)).append("\" ");
			if (key.equals(this.value)) {
				buffer.append("selected");
			}// end if
			buffer.append(">").append(escapeHtml(value)).append("</option>");
		}// end for
		buffer.append("</select>");
		if (null != error) {
			buffer.append("<span class=\"error\">").append(escapeHtml(error)).append("</span>");
		}// end if
		buffer.append("</p>");
		try {
			pageContext.getOut().print(buffer.toString());
			pageContext.getOut().flush();
		} catch (Exception e) {
			logger.error("Unable to access the JspWriter to render dropbox tag. Message is: " + e.getMessage(), e);
		}// end try/catch
		return EVAL_PAGE;
	}// end doStartTag

	/**
	 * sets the qualified path
	 * 
	 * @param qualifiedPath the qualifiedPath to set
	 */
	public void setQualifiedPath(String qualifiedPath) {
		this.qualifiedPath = qualifiedPath;
	}// end setQualifiedPath
}// end class
