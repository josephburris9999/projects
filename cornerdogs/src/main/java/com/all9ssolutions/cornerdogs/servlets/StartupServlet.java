// Copyright (c) 2024 Joseph Burris, all9s Solutions LLC
// SPDX-License-Identifier: BSD-3-Clause

package com.all9ssolutions.cornerdogs.servlets;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.all9ssolutions.cornerdogs.models.IConstants;

/**
 * Servlet implementation class StartupServlet
 * 
 * @see jakarta.servlet.http.HttpServlet
 * @author Joseph Burris, all9s Solutions LLC
 */
public class StartupServlet extends HttpServlet implements IConstants {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger("com.all9ssolutions.cornerdogs.servlets.StartupServlet");
	@Override
	public void init() throws ServletException {
		super.init();
		logger.debug("Initializing application constants.");
		InitParameters.LOG_DIRECTORY = getInitParameter("LOG_DIRECTORY").trim();
		try {
			Files.createDirectories(
					FileSystems.getDefault().getPath(new File(InitParameters.LOG_DIRECTORY).getAbsolutePath()));
		} catch (IOException e) {
			logger.debug(e.getMessage());
		}// end try/catch
		InitParameters.HOT_DOG_PRICE = getInitParameter("HOT_DOG_PRICE").trim();
		InitParameters.TAX_RATE = getInitParameter("TAX_RATE").trim();
		logger.info(InitParameters.print());
	}// end init
}// end class
