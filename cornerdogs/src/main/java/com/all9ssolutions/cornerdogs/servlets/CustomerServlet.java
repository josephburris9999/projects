// Copyright (c) 2024 Joseph Burris, all9s Solutions LLC
// SPDX-License-Identifier: BSD-3-Clause

package com.all9ssolutions.cornerdogs.servlets;

import jakarta.servlet.annotation.WebServlet;

import com.all9ssolutions.cornerdogs.models.Customer;

/**
 * Servlet implementation class CustomerServlet
 * 
 * @see com.all9ssolutions.cornerdogs.servlets.AbstractServlet
 */
@WebServlet( //
		description = "processes a customer form", //
		urlPatterns = { //
				"/customer" //
		})
/**
 * @author Joseph Burris, all9s Solutions LLC
 */
public class CustomerServlet extends AbstractServlet {
	private static final long serialVersionUID = 1L;
	@Override
	protected String getObjectName() {
		return Customer.class.getCanonicalName();
	}// end getObjectName
	@Override
	protected String getPreviousTitle() {
		return "Welcome";
	}// end getPreviousTitle
	@Override
	protected String getPreviousPath() {
		return "/index.jsp";
	}// end getPreviousPath
	@Override
	protected String getCurrentTitle() {
		return "Customer";
	}// end getCurrentTitle
	@Override
	protected String getCurrentPath() {
		return "/pages/customer.jsp";
	}// end getCurrentPath
	@Override
	protected String getNextTitle() {
		return "Order";
	}// end getNextTitle
	@Override
	protected String getNextPath() {
		return "/pages/order.jsp";
	}// end getNextPath
}// end class
