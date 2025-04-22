package org.eclipse.jface.examples.databinding.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.examples.databinding.ModelObject;

public class Catalog extends ModelObject {

	private Category[] categories = new Category[0];

	private Lodging[] lodgings = new Lodging[0];

	private Transportation[] transportations = new Transportation[0];

	private Account[] accounts = new Account[0];

	private List signons = new ArrayList();

	public List getSignons(){
		return signons;
	}

	public void addSignon(Signon aSignon){
		signons.add(aSignon);
		firePropertyChange("signons",null,null);
	}

	public void removeSignon(Signon aSignon){
		signons.remove(aSignon);
		firePropertyChange("signons",null,null);
	}

	public Category[] getCategories() {
		return categories;
	}

	public void addCategory(Category category) {
		categories = (Category[]) append(categories, category);
		firePropertyChange("categories", null, null);
	}

	public void addLodging(Lodging lodging) {
		lodgings = (Lodging[]) append(lodgings, lodging);
		firePropertyChange("lodgings", null, null);
	}

	public void addTransportation(Transportation transportation) {
		transportations = (Transportation[]) append(transportations, transportation);
		firePropertyChange("transportations", null, null);
	}

	public void addAccount(Account account) {
		accounts = (Account[]) append(accounts, account);
		firePropertyChange("accounts", null, null);
	}

	public Lodging[] getLodgings() {
		return lodgings;
	}

	public void removeLodging(Lodging lodging) {
		lodgings = (Lodging[]) remove(lodgings, lodging);
		firePropertyChange("lodgings", null, null);
	}

	public void removeAccount(Account anAccount) {
		accounts = (Account[]) remove(accounts, anAccount);
		firePropertyChange("accounts", null, null);
	}

	public Account[] getAccounts() {
		return accounts;
	}

	public Transportation[] getTransporations(){
		return transportations;
	}

}
