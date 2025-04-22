package org.eclipse.jface.examples.databinding.model;

import org.eclipse.jface.examples.databinding.ModelObject;


public class PriceModelObject extends ModelObject {

	private double price;

	public double getDouble(){
		return price;
	}
	public void setPrice(double aPrice){
		int oldDollars = getDollars();
		int oldCents = getCents();
		double oldValue = price;
		price = aPrice;
		firePropertyChange("dollars",oldDollars,getDollars());
		firePropertyChange("cents",oldCents,getCents());
		firePropertyChange("price",new Double(oldValue), new Double(price));
	}

	public double getPrice(){
		return price;
	}

	public int getCents(){
		return (int) (100*price - 100*Math.floor(price));
	}

	public void setCents(int cents){
		double oldPrice = getPrice();
		int oldCents = getCents();
		price = getDollars() + cents *.01;
		firePropertyChange("cents",oldCents,getCents());
		firePropertyChange("price", new Double(oldPrice), new Double(price));
	}

	public int getDollars(){
		return new Double(price).intValue();
	}

	public void setDollars(int dollars){
		double oldPrice = getPrice();
		int oldDollars = getDollars();
		price = dollars + getCents() *.01;
		firePropertyChange("dollars",oldDollars,getDollars());
		firePropertyChange("price", new Double(oldPrice), new Double(price));
	}

}
