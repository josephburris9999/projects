// Copyright (c) 2024 Joseph Burris, all9s Solutions LLC
// SPDX-License-Identifier: BSD-3-Clause

package com.all9ssolutions.cornerdogs.servlets;

import jakarta.servlet.annotation.WebServlet;

import com.all9ssolutions.cornerdogs.models.Order;

/**
 * Servlet implementation class OrderServlet
 * 
 * @see com.all9ssolutions.cornerdogs.servlets.AbstractServlet
 */
@WebServlet( //
		description = "processes an order form", //
		urlPatterns = { //
				"/order" //
		})
/**
 * @author Joseph Burris, all9s Solutions LLC
 */
public class OrderServlet extends AbstractServlet {
	private static final long serialVersionUID = 1L;
	@Override
	protected String getObjectName() {
		return Order.class.getCanonicalName();
	}// end getObjectName
	@Override
	protected String getPreviousTitle() {
		return "Customer";
	}// end getPreviousTitle
	@Override
	protected String getPreviousPath() {
		return "/pages/customer.jsp";
	}// end getPreviousPath
	@Override
	protected String getCurrentTitle() {
		return "Order";
	}// end getCurrentTitle
	@Override
	protected String getCurrentPath() {
		return "/pages/order.jsp";
	}// end getCurrentPath
	@Override
	protected String getNextTitle() {
		return "Verification";
	}// end getNextTitle
	@Override
	protected String getNextPath() {
		return "/pages/verification.jsp";
	}// end getNextPath
}// end class
