// Copyright (c) 2024 Joseph Burris, all9s Solutions LLC
// SPDX-License-Identifier: BSD-3-Clause

package com.all9ssolutions.cornerdogs.servlets;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CancelServlet
 * 
 * @see jakarta.servlet.http.HttpServlet
 */
@WebServlet( //
		description = "handles canceling a form", //
		urlPatterns = { //
				"/cancel" //
		})
/**
 * @author Joseph Burris, all9s Solutions LLC
 */
public class CancelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession().invalidate();
		getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
	}// end doGet
}// end class
