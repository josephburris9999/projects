// Copyright (c) 2024 Joseph Burris, all9s Solutions LLC
// SPDX-License-Identifier: BSD-3-Clause

package com.all9ssolutions.cornerdogs.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Locale;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.all9ssolutions.cornerdogs.enums.Where;

class OrderTest {
	private Locale defaultLocale;

	@BeforeEach
	void setUp() {
		defaultLocale = Locale.getDefault();
		Locale.setDefault(Locale.US);
		IConstants.InitParameters.HOT_DOG_PRICE = ".99";
		IConstants.InitParameters.TAX_RATE = ".0825";
	}

	@AfterEach
	void tearDown() {
		Locale.setDefault(defaultLocale);
	}

	@Test
	void setValuesAcceptsValidOrderAndCalculatesTotals() {
		Order order = new Order();

		order.setValues(Map.of( //
				"submit", new String[] { "next" }, //
				"where", new String[] { "HERE" }, //
				"hotDogs", new String[] { "2" }, //
				"withEverything", new String[] { "3" }));

		assertTrue(order.getErrors().isEmpty());
		assertEquals("next", order.getSubmit());
		assertEquals(Where.HERE, order.getWhere());
		assertEquals(5, order.getHotDogCount());
		assertEquals("$1.98", order.getHotDogsPrice());
		assertEquals("$2.97", order.getWithEverythingPrice());
		assertEquals("$4.95", order.getSubTotal());
		assertEquals("$0.41", order.getTax());
		assertEquals("$5.36", order.getTotal());
	}

	@Test
	void setValuesRequiresAtLeastOneHotDog() {
		Order order = new Order();

		order.setValues(Map.of( //
				"submit", new String[] { "next" }, //
				"where", new String[] { "TOGO" }, //
				"hotDogs", new String[] { "0" }, //
				"withEverything", new String[] { "0" }));

		assertEquals("Must have at least one hot dog in your order.", order.getError("orderError"));
	}

	@Test
	void setValuesRejectsInvalidNumberInput() {
		Order order = new Order();

		order.setValues(Map.of( //
				"submit", new String[] { "next" }, //
				"where", new String[] { "HERE" }, //
				"hotDogs", new String[] { "x" }, //
				"withEverything", new String[] { "1" }));

		assertEquals("hot dogs must only be digits.", order.getError("hotDogsError"));
		assertEquals(1, order.getHotDogCount());
	}
}
