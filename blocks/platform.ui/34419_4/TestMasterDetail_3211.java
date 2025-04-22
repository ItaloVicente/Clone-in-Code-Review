package org.eclipse.jface.examples.databinding.model;

import org.eclipse.jface.examples.databinding.ModelObject;

public class Transportation extends ModelObject {

	private String arrivalTime;
	private double price;

	public void setArrivalTime(String string) {
		String oldValue = arrivalTime;
		arrivalTime = string;
		firePropertyChange("arrivaltime",oldValue,string);
	}

	public String getArrivalTime(){
		return arrivalTime;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double aPrice) {
		double oldPrice = price;
		price = aPrice;
		firePropertyChange("price",new Double(oldPrice),new Double(price));
	}

}
