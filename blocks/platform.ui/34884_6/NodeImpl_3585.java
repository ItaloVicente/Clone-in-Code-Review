package org.eclipse.e4.emf.xpath.test.model.xpathtest.impl;

import org.eclipse.e4.emf.xpath.test.model.xpathtest.MenuItem;
import org.eclipse.e4.emf.xpath.test.model.xpathtest.XpathtestPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

public class MenuItemImpl extends MenuElementImpl implements MenuItem {
	protected static final char MNEMONIC_EDEFAULT = '\u0000';

	protected char mnemonic = MNEMONIC_EDEFAULT;

	protected MenuItemImpl() {
		super();
	}

	@Override
	protected EClass eStaticClass() {
		return XpathtestPackage.Literals.MENU_ITEM;
	}

	public char getMnemonic() {
		return mnemonic;
	}

	public void setMnemonic(char newMnemonic) {
		char oldMnemonic = mnemonic;
		mnemonic = newMnemonic;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, XpathtestPackage.MENU_ITEM__MNEMONIC, oldMnemonic, mnemonic));
	}

	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case XpathtestPackage.MENU_ITEM__MNEMONIC:
				return getMnemonic();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case XpathtestPackage.MENU_ITEM__MNEMONIC:
				setMnemonic((Character)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case XpathtestPackage.MENU_ITEM__MNEMONIC:
				setMnemonic(MNEMONIC_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case XpathtestPackage.MENU_ITEM__MNEMONIC:
				return mnemonic != MNEMONIC_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (mnemonic: ");
		result.append(mnemonic);
		result.append(')');
		return result.toString();
	}

} //MenuItemImpl
