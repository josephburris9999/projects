// Copyright (c) 2024 Joseph Burris, all9s Solutions LLC
// SPDX-License-Identifier: BSD-3-Clause

package com.all9ssolutions.cornerdogs.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class BreadcrumbTest {
	@Test
	void putDoesNotOverwriteExistingBreadcrumbLabel() {
		Breadcrumb breadcrumb = new Breadcrumb();

		breadcrumb.put("/pages/customer.jsp", "Customer");
		breadcrumb.put("/pages/customer.jsp", "Changed");

		assertEquals("Customer", breadcrumb.get("/pages/customer.jsp"));
		assertEquals(1, breadcrumb.size());
	}
}
