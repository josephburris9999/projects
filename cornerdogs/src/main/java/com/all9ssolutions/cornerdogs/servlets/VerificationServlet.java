// Copyright (c) 2024 Joseph Burris, all9s Solutions LLC
// SPDX-License-Identifier: BSD-3-Clause

package com.all9ssolutions.cornerdogs.servlets;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.all9ssolutions.cornerdogs.models.Verification;

/**
 * Servlet implementation class VerificationServlet
 * 
 * @see com.all9ssolutions.cornerdogs.servlets.AbstractServlet
 */
@WebServlet( //
		description = "processes a verification form", //
		urlPatterns = { //
				"/verification" //
		})
/**
 * @author Joseph Burris, all9s Solutions LLC
 */
public class VerificationServlet extends AbstractServlet {
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (null == request.getParameter("submit")) {
			super.doGet(request, response);
		} else {
			doPost(request, response);
		}// end if/else
	}// end doGet
	@Override
	protected String getObjectName() {
		return Verification.class.getCanonicalName();
	}// end getObjectName
	@Override
	protected String getPreviousTitle() {
		return "Order";
	}// end getPreviousTitle
	@Override
	protected String getPreviousPath() {
		return "/pages/order.jsp";
	}// end getPreviousPath
	@Override
	protected String getCurrentTitle() {
		return "Verification";
	}// end getCurrentTitle
	@Override
	protected String getCurrentPath() {
		return "/pages/verification.jsp";
	}// end getCurrentPath
	@Override
	protected String getNextTitle() {
		return "Confirmation";
	}// end getNextTitle
	@Override
	protected String getNextPath() {
		return "/pages/confirmation.jsp";
	}// end getNextPath
	@Override
	protected boolean isLastPage() {
		return true;
	}// end isLastPage
}// end class
