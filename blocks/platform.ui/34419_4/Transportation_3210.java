package org.eclipse.jface.examples.databinding.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.examples.databinding.ModelObject;

public class SimplePerson extends ModelObject {

	private String name = "";
	private String address = "";
	private String city = "";
	private String state = "";
	private SimpleCart cart = new SimpleCart();

	private List orders = new LinkedList();

	public SimplePerson(String name, String address, String city, String state) {
		this.name = name;
		this.address = address;
		this.city = city;
		this.state = state;

		int numOrders = (int) (Math.random() * 5);
		for (int i=0; i < numOrders; ++i) {
			orders.add(new SimpleOrder(i, new Date()));
		}
	}

	public SimplePerson() {}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		String old = this.address;
		this.address = address;
		firePropertyChange("address", old, address);
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		String old = this.city;
		firePropertyChange("city", old, this.city = city);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		firePropertyChange("name", this.name, this.name = name);
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		firePropertyChange("state", this.state, this.state = state); //$NON-NLS-1$
	}

	public List getOrders() {
		return orders;
	}

	public SimpleCart getCart() {
		return cart;
	}

	public void setCart(SimpleCart cart) {
		firePropertyChange("cart", this.cart, this.cart = cart); //$NON-NLS-1$
	}
}
