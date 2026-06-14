// Copyright (c) 2024 Joseph Burris, all9s Solutions LLC
// SPDX-License-Identifier: BSD-3-Clause

package com.all9ssolutions.cornerdogs.controllers;

import java.io.Serializable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.all9ssolutions.cornerdogs.models.Customer;
import com.all9ssolutions.cornerdogs.models.IConstants;

/**
 * Handles the final demo-only submission.
 * @author Joseph Burris, all9s Solutions LLC
 */
public class Processor implements IConstants, Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger("com.all9ssolutions.cornerdogs.controllers.Processor");

	public Processor() {
		super();
	}// end constructor

	/**
	 * Logs the submitted customer without persisting or forwarding the order.
	 *
	 * @param customer the submitted customer and order details
	 */
	public void save(Customer customer) {
		logger.info("Demo-only order submitted; no persistence was performed.");
	}// end save
}// end class
