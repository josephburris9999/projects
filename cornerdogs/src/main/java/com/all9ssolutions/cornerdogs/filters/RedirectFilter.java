// Copyright (c) 2024 Joseph Burris, all9s Solutions LLC
// SPDX-License-Identifier: BSD-3-Clause

package com.all9ssolutions.cornerdogs.filters;

import static com.all9ssolutions.cornerdogs.models.IConstants.CUSTOMER_SESSION_KEY;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Servlet Filter implementation class RedirectFilter.
 * 
 * @see jakarta.servlet.http.HttpFilter
 */
@WebFilter( //
		description = "filters all requests to insure a customer is in session", //
		urlPatterns = { "*" } //
)
/**
 * @author Joseph Burris, all9s Solutions LLC
 */
public class RedirectFilter extends HttpFilter implements Filter {
	private static final long serialVersionUID = 1L;
	private FilterConfig config;
	@Override
	public void init(FilterConfig config) throws ServletException {
		this.config = config;
	}// end init
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String url = ((HttpServletRequest) request).getRequestURL().toString();
		if (url.endsWith("/")) {
			url = url.substring(0, url.length() - 1);
		}// end if
		// Ignore these paths for validation.
		if (url.endsWith(((HttpServletRequest) request).getContextPath()) //
				|| url.endsWith("/customer") //
				|| url.endsWith("confirmation.jsp") //
				|| url.endsWith("privacy.jsp") //
				|| url.endsWith("terms.jsp") //
				|| url.contains("/css/") //
				|| url.contains("/fonts/") //
				|| url.contains("/images/") //
				|| url.contains("/js/")) {
			chain.doFilter(request, response);
		} else {
			if (null == ((HttpServletRequest) request).getSession().getAttribute(CUSTOMER_SESSION_KEY)) {
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
				dispatcher.forward(request, response);
			} else {
				chain.doFilter(request, response);
			}// end if/else
		}// end if/else
	}// end doFilter
	@Override
	public void destroy() {
		this.config = null;
	}// end destroy
	@Override
	public FilterConfig getFilterConfig() {
		return config;
	}// end getFilterConfig
}// end class
