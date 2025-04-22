package org.eclipse.e4.emf.xpath.test.model.xpathtest.impl;

import java.util.Collection;

import org.eclipse.e4.emf.xpath.test.model.xpathtest.Menu;
import org.eclipse.e4.emf.xpath.test.model.xpathtest.MenuElement;
import org.eclipse.e4.emf.xpath.test.model.xpathtest.XpathtestPackage;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

public class MenuImpl extends MenuElementImpl implements Menu {
	protected EList<MenuElement> children;

	protected MenuImpl() {
		super();
	}

	@Override
	protected EClass eStaticClass() {
		return XpathtestPackage.Literals.MENU;
	}

	public EList<MenuElement> getChildren() {
		if (children == null) {
			children = new EObjectContainmentEList<MenuElement>(MenuElement.class, this, XpathtestPackage.MENU__CHILDREN);
		}
		return children;
	}

	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case XpathtestPackage.MENU__CHILDREN:
				return ((InternalEList<?>)getChildren()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case XpathtestPackage.MENU__CHILDREN:
				return getChildren();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case XpathtestPackage.MENU__CHILDREN:
				getChildren().clear();
				getChildren().addAll((Collection<? extends MenuElement>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case XpathtestPackage.MENU__CHILDREN:
				getChildren().clear();
				return;
		}
		super.eUnset(featureID);
	}

	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case XpathtestPackage.MENU__CHILDREN:
				return children != null && !children.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //MenuImpl
