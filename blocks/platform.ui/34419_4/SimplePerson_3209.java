
package org.eclipse.jface.examples.databinding.model;

import java.util.Date;

import org.eclipse.jface.examples.databinding.ModelObject;

public class SimpleOrder extends ModelObject {

	private int orderNumber;
	private Date date;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public SimpleOrder(int i, Date date) {
		this.orderNumber = i;
		this.date = date;
	}

}
