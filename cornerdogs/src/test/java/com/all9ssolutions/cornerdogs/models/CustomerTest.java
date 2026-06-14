// Copyright (c) 2024 Joseph Burris, all9s Solutions LLC
// SPDX-License-Identifier: BSD-3-Clause

package com.all9ssolutions.cornerdogs.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.Map;

import org.junit.jupiter.api.Test;

class CustomerTest {
	@Test
	void setValuesStoresTrimmedCustomerName() {
		Customer customer = new Customer();

		customer.setValues(Map.of( //
				"submit", new String[] { "next" }, //
				"name", new String[] { "  Joseph  " }));

		assertEquals("next", customer.getSubmit());
		assertEquals("Joseph", customer.getName());
	}

	@Test
	void setValuesRejectsMissingCustomerName() {
		Customer customer = new Customer();

		customer.setValues(Map.of( //
				"submit", new String[] { "next" }, //
				"name", new String[] { " " }));

		assertEquals("Name is required.", customer.getError("nameError"));
	}

	@Test
	void getObjectReturnsWizardModelByCanonicalName() {
		Customer customer = new Customer();

		assertSame(customer.getOrder(), customer.getObject(Order.class.getCanonicalName()));
		assertSame(customer.getVerification(), customer.getObject(Verification.class.getCanonicalName()));
		assertSame(customer, customer.getObject(Customer.class.getCanonicalName()));
	}
}
