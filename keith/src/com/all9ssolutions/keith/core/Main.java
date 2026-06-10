/*
 * Keith Framework
 * Copyright (c) 2026 Joseph Burris, all9s Solutions LLC
 *
 * Licensed under the MIT License. See LICENSE file in the project root.
 */
package com.all9ssolutions.keith.core;

import java.util.HashMap;
import java.util.Map;

/**
 * This class models the expected structure for an application implementing the Keith framework.
 * 
 * @author Joseph Burris, all9s Solutions LLC
 */
public class Main extends AbstractApplication {
	private static final long serialVersionUID = 1L;

	/**
	 * overloaded constructor instantiates this class
	 * <p>
	 * once instantiated the flow of control is passed to the super class
	 * </p>
	 * 
	 * @param args the {@code String[]} of command line arguments
	 */
	public Main(String[] args) {
		super(args);
	}

	/**
	 * entry point for executing a stand-alone Java application
	 *
	 * @param args the {@code String[]} of command line arguments
	 */
	public static void main(String[] args) {
		new Main(args);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		getApplicationProperties().list(System.out);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Map<String, String> getProperties() {
		Map<String, String> map = new HashMap<>();
		map.put("check.period", "3000");
		return map;
	}
}
