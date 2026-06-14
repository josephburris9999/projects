// Copyright (c) 2024 Joseph Burris, all9s Solutions LLC
// SPDX-License-Identifier: BSD-3-Clause

package com.all9ssolutions.cornerdogs.models;

import java.util.Map;

/**
 * This class models a customer visit spanning multiple JSP's.
 * 
 * @see com.all9ssolutions.cornerdogs.models.AbstractModel
 * @author Joseph Burris, all9s Solutions LLC
 */
public class Customer extends AbstractModel {
	private static final long serialVersionUID = 1L;
	private String name;
	private Order order;
	private Verification verification;

	/**
	 * default constructor instantiates this class
	 */
	public Customer() {
		super();
		this.order = new Order();
		this.verification = new Verification();
	}// end constructor
	@Override
	public void setValues(Map<String, String[]> map) {
		setSubmit(getFirst(map, "submit"));
		setName(getFirst(map, "name"));

	}// end setValues

	/**
	 * returns the sub-class of {@link com.all9ssolutions.cornerdogs.models.AbstractModel} for the canonical
	 * name
	 * 
	 * @param name the name to test against
	 * @return the class variable with the name matching the parameter
	 */
	public AbstractModel getObject(String name) {
		if (Order.class.getCanonicalName().equals(name)) {
			return getOrder();
		} else if (Verification.class.getCanonicalName().equals(name)) {
			return getVerification();
		} else {
			return this;
		} // end if/else
	}// end getObject
	@Override
	public String toString() {
		return "Customer [name=" + name + ", order=" + order + ", verification=" + verification + ", getUid()="
				+ getUid() + ", getErrors()=" + getErrors() + ", getSubmit()=" + getSubmit() + ", toString()="
				+ super.toString() + "]";
	}// end toString

	/**
	 * returns the customer name
	 * 
	 * @return the name of the customer
	 */
	public String getName() {
		return null != name ? name.trim() : "";

	}// end getName

	/**
	 * sets the customer name
	 * 
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
		if (validateString("nameError", name, "Name", 25)) {
			this.name = name;
		} // end if
	}// end setName

	/**
	 * returns the customer order
	 * 
	 * @return the customer {@link com.all9ssolutions.cornerdogs.models.Order}
	 */
	public Order getOrder() {
		return order;
	}// end getOrder

	/**
	 * sets the customer order
	 * 
	 * @param order the {@link com.all9ssolutions.cornerdogs.models.Order} to set
	 */
	public void setOrder(Order order) {
		this.order = order;
	}// end setOrder

	/**
	 * returns the customer verification
	 * 
	 * @return the customer {@link com.all9ssolutions.cornerdogs.models.Verification}
	 */
	public Verification getVerification() {
		return verification;
	}// end getVerification

	/**
	 * sets the customer verification
	 * 
	 * @param verification the {@link com.all9ssolutions.cornerdogs.models.Verification} to set
	 */
	public void setVerification(Verification verification) {
		this.verification = verification;
	}// end setVerification
}// end class
