// Copyright (c) 2024 Joseph Burris, all9s Solutions LLC
// SPDX-License-Identifier: BSD-3-Clause

package com.all9ssolutions.cornerdogs.models;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

import com.all9ssolutions.cornerdogs.enums.Where;

/**
 * This class models the customer order from the order JSP.
 * 
 * @see com.all9ssolutions.cornerdogs.models.AbstractModel
 * @author Joseph Burris, all9s Solutions LLC
 */
public class Order extends AbstractModel {
	private static final long serialVersionUID = 1L;
	private Timestamp when;
	private Where where;
	private int hotDogs;
	private int withEverything;

	/**
	 * default constructor instantiates this class
	 */
	public Order() {
		super();
		this.when = new Timestamp(System.currentTimeMillis());
	}// end constructor
	@Override
	public void setValues(Map<String, String[]> map) {
		setSubmit(getFirst(map, "submit"));
		setWhere(getFirst(map, "where"));
		setHotDogs(getFirst(map, "hotDogs"));
		setWithEverything(getFirst(map, "withEverything"));
		if (0 == getHotDogs() && 0 == getWithEverything()) {
			super.getErrors().put("orderError", "Must have at least one hot dog in your order.");
		} // end if
	}// end setValues

	/**
	 * returns the formatted order timestamp
	 * 
	 * @return the order timestamp in M/d/yy H:m a format
	 */
	public String getFormattedWhen() {
		return new SimpleDateFormat(SDF_TIMESTAMP).format(when);
	}// end getFormattedWhen

	/**
	 * returns the total number of hot dogs ordered
	 * 
	 * @return the sum of hot dogs and hot dogs with everything ordered
	 */
	public int getHotDogCount() {
		return getHotDogs() + getWithEverything();
	}

	/**
	 * returns the price of the ordered hot dogs
	 * 
	 * @return the ordered hot dogs price in currency format
	 */
	public String getHotDogsPrice() {
		return getPrice(getHotDogs());
	}// end getHotDogsPrice

	/**
	 * returns the price of the order hot dogs with everything
	 * 
	 * @return the ordered hot dogs with everything price in currency format
	 */
	public String getWithEverythingPrice() {
		return getPrice(getWithEverything());
	}// end getWithEverythingPrice

	/**
	 * returns the sub-total for both the ordered hot dogs and hot dogs with everything
	 * 
	 * @return the ordered hot dogs and hot dogs with everything sub-total in currency format
	 */
	public String getSubTotal() {
		return getPrice(getHotDogCount());
	}// end getSubTotal

	/**
	 * returns the price of the hot dog multiplied by the number of hot dogs ordered
	 * 
	 * @param n the number of hot dogs ordered
	 * @return the number of ordered hot dogs price in currency format
	 */
	public String getPrice(int n) {
		if (0 != n) {
			BigDecimal price = getMultiplied(n, InitParameters.HOT_DOG_PRICE);
			return getFormatted(price);
		} // end if
		return "";
	}// end getPrice

	/**
	 * returns the tax for both the ordered hot dogs and hot dogs with everything
	 * 
	 * @return the ordered hot dogs and hot dogs with everything tax in currency format
	 */
	public String getTax() {
		int n = getHotDogCount();
		if (0 != n) {
			BigDecimal tax = getMultiplied(n, InitParameters.TAX_RATE);
			return getFormatted(tax);
		} // end if
		return "";
	}// end getTax

	/**
	 * returns the total for both the ordered hot dogs and hot dogs with everything plus tax
	 * 
	 * @return the ordered hot dogs and hot dogs with everything total plus tax in currency format
	 */
	public String getTotal() {
		int n = getHotDogCount();
		if (0 != n) {
			BigDecimal price = getMultiplied(n, InitParameters.HOT_DOG_PRICE);
			BigDecimal tax = getMultiplied(n, InitParameters.TAX_RATE);
			BigDecimal total = price.add(tax);
			return getFormatted(total);
		} // end if
		return "";
	}// end getTotal

	/**
	 * multiplies a currency amount by the ordered quantity
	 * 
	 * @param n     the ordered quantity
	 * @param param the currency amount
	 * @return a {@link java.math.BigDecimal} result of the multiplication
	 */
	private BigDecimal getMultiplied(int n, String param) {
		if (0 != n) {
			BigDecimal price = new BigDecimal(param);
			return price.multiply(new BigDecimal(n));
		} // end if
		return BigDecimal.ZERO;
	}// end getMultiplied

	/**
	 * formats the parameter in currency format
	 * 
	 * @param decimal the parameter to format
	 * @return the currency formatted value of the parameter
	 */
	private String getFormatted(BigDecimal decimal) {
		NumberFormat format = NumberFormat.getCurrencyInstance();
		format.setMinimumFractionDigits(2);
		return format.format(decimal);
	}// end getFormatted
	@Override
	public String toString() {
		return "Order [when=" + when + ", where=" + where + ", hotDogs=" + hotDogs + ", withEverything=" + withEverything + ", getUid()=" + getUid() + ", getErrors()=" + getErrors() + ", getSubmit()=" + getSubmit() + ", toString()=" + super.toString() + "]";
	}// end toString

	/**
	 * returns the order timestamp
	 * 
	 * @return the timestamp of the order
	 */
	public Timestamp getWhen() {
		return when;
	}// end getWhen

	/**
	 * sets the order timestamp
	 * 
	 * @param when the timestamp to set
	 */
	public void setWhen(String when) {
		if (validateDate("whenError", when, "When", "future")) {
			this.when = parseTimestamp(when);
		} // end if
	}// end setWhen

	/**
	 * sets the order timestamp
	 * 
	 * @param when the timestamp to set
	 */
	public void setWhen(Timestamp when) {
		this.when = when;
	}// end setWhen

	/**
	 * returns the where to receive the order
	 * 
	 * @return where to receive the order
	 */
	public Where getWhere() {
		return null != where ? where : Where.EMPTY;
	}// end getWhere

	/**
	 * sets where to receive the order
	 * 
	 * @param where where to receive the order
	 */
	public void setWhere(String where) {
		if (validateString("whereError", where, "For here or to go", 4)) {
			try {
				this.where = !"".equals(where) ? Where.valueOf(where) : Where.EMPTY;
			} catch (IllegalArgumentException e) {
				super.getErrors().put("whereError", "For here or to go must be a valid option.");
			}// end try/catch
		} // end if
	}// end setWhere

	/**
	 * returns the number of hot dogs ordered
	 * 
	 * @return the quantity of hot dogs for the order
	 */
	public int getHotDogs() {
		return hotDogs;
	}// end getHotDogs

	/**
	 * set the number of hot dogs ordered
	 * 
	 * @param hotDogs the quantity of hot dogs ordered
	 */
	public void setHotDogs(String hotDogs) {
		if (validateNumber("hotDogsError", hotDogs, "hot dogs", 2)) {
			this.hotDogs = Integer.parseInt(hotDogs);
		} // end if
	}// end setHotDogs

	/**
	 * set the number of hot dogs ordered
	 * 
	 * @param hotDogs the quantity of hot dogs ordered
	 */
	public void setHotDogs(int hotDogs) {
		this.hotDogs = hotDogs;
	}// end setHotDogs

	/**
	 * returns the number of hot dogs with everything ordered
	 * 
	 * @return the quantity of hot dogs with everything for the order
	 */
	public int getWithEverything() {
		return withEverything;
	}// end getWithEverything

	/**
	 * set the number of hot dogs with everything ordered
	 * 
	 * @param hotDogs the quantity of hot dogs with everything ordered
	 */
	public void setWithEverything(String withEverything) {
		if (validateNumber("withEverythingError", withEverything, "hot dogs with everything", 2)) {
			this.withEverything = Integer.parseInt(withEverything);
		} // end if
	}// end setWithEverything

	/**
	 * set the number of hot dogs with everything ordered
	 * 
	 * @param hotDogs the quantity of hot dogs with everything ordered
	 */
	public void setWithEverything(int withEverything) {
		this.withEverything = withEverything;
	}// end setWithEverything
}// end class
