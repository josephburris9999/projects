// Copyright (c) 2024 Joseph Burris, all9s Solutions LLC
// SPDX-License-Identifier: BSD-3-Clause

package com.all9ssolutions.cornerdogs.servlets;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.all9ssolutions.cornerdogs.controllers.Processor;
import com.all9ssolutions.cornerdogs.models.AbstractModel;
import com.all9ssolutions.cornerdogs.models.Breadcrumb;
import com.all9ssolutions.cornerdogs.models.Customer;
import com.all9ssolutions.cornerdogs.models.IConstants;

/**
 * Super-class for servlets in the application.
 * 
 * @see jakarta.servlet.http.HttpServlet
 * @author Joseph Burris, all9s Solutions LLC
 */
public abstract class AbstractServlet extends HttpServlet implements IConstants {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger("com.all9ssolutions.cornerdogs.servlets.AbstractServlet");
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Breadcrumb breadcrumb = getBreadcrumb(request);
		breadcrumb.put(getCurrentPath(), getCurrentTitle());
		request.getSession().setAttribute(BREADCRUMB_SESSION_KEY, breadcrumb);
		Customer customer = getCustomer(request);
		request.getSession().setAttribute(CUSTOMER_SESSION_KEY, customer);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(getCurrentPath());
		dispatcher.forward(request, response);
	}// end doGet
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Breadcrumb breadcrumb = getBreadcrumb(request);
		breadcrumb.put(getCurrentPath(), getCurrentTitle());
		Customer customer = getCustomer(request);
		AbstractModel object = customer.getObject(getObjectName());
		object.clearErrors();
		object.setValues(request.getParameterMap());
		RequestDispatcher dispatcher;
		if (null != object.getSubmit() && !"".equals(object.getSubmit().trim())) {
			if ("previous".equalsIgnoreCase(object.getSubmit())) {
				dispatcher = getServletContext().getRequestDispatcher(getPreviousPath());
			} else if ("next".equalsIgnoreCase(object.getSubmit())) {
				if (object.getErrors().isEmpty()) {
					if (isLastPage()) {
						new Processor().save(customer);
						request.getSession().invalidate();
					}// end if
					breadcrumb.put(getNextPath(), getNextTitle());
					dispatcher = getServletContext().getRequestDispatcher(getNextPath());
				} else {
					dispatcher = getServletContext().getRequestDispatcher(getCurrentPath());
					request.getSession().setAttribute(CUSTOMER_SESSION_KEY, customer);
				}// end if/else
			} else {
				throw new RuntimeException("Unknown submit selected for path: " + request.getRequestURI());
			}// end if/else
		} else {
			dispatcher = getServletContext().getRequestDispatcher(getCurrentPath());
			request.getSession().setAttribute(CUSTOMER_SESSION_KEY, customer);
		}// end if/else
		dispatcher.forward(request, response);
	}// end doPost

	/**
	 * returns the customer from the session
	 * 
	 * @param request the client request
	 * @return the {@link com.all9ssolutions.cornerdogs.models.Customer} in session
	 */
	protected Customer getCustomer(HttpServletRequest request) {
		Customer customer = (Customer) request.getSession().getAttribute(CUSTOMER_SESSION_KEY);
		if (null == customer) {
			logger.debug("The customer does not exist in session. Setting it now.");
			customer = new Customer();
		}// end if
		return customer;
	}// end getCustomer

	/**
	 * returns the breadcrumb from the session
	 * 
	 * @param request the client request
	 * @return the {@link com.all9ssolutions.cornerdogs.models.Breadcrumb} in session
	 */
	protected Breadcrumb getBreadcrumb(HttpServletRequest request) {
		Breadcrumb breadcrumb = (Breadcrumb) request.getSession().getAttribute(BREADCRUMB_SESSION_KEY);
		if (null == breadcrumb) {
			breadcrumb = new Breadcrumb();
			breadcrumb.put("/index.jsp", "Welcome");
		}// end if
		return breadcrumb;
	}// end getBreadcrumb

	/**
	 * returns the name of the sub-class
	 * 
	 * @return the name of the sub-class backing the JSP
	 */
	protected abstract String getObjectName();

	/**
	 * returns the title of the page last displayed
	 * 
	 * @return the title for the page previously rendered by the servlet
	 */
	protected abstract String getPreviousTitle();

	/**
	 * returns the path of the page last displayed
	 * 
	 * @return the context path of the servlet previously rendered
	 */
	protected abstract String getPreviousPath();

	/**
	 * returns the title of the current page
	 * 
	 * @return the title for the page being rendered by the servlet
	 */
	protected abstract String getCurrentTitle();

	/**
	 * returns the path of the current page
	 * 
	 * @return the context path of the JSP being rendered by the servlet.
	 */
	protected abstract String getCurrentPath();

	/**
	 * returns the title of the page to display next
	 * 
	 * @return the title for the next page to be rendered by the servlet
	 */
	protected abstract String getNextTitle();

	/**
	 * returns the path of the page to display next
	 * 
	 * @return the context path of the next servlet to render
	 */
	protected abstract String getNextPath();

	/**
	 * returns whether this is the last page for the wizard
	 * 
	 * @return {@code true} if the current servlet sub-class marks the last available page.
	 */
	protected boolean isLastPage() {
		return false;
	}// end isLastPage
}// end class
