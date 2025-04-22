package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.impl;


import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.Arena;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.HockeyleaguePackage;

public class ArenaImpl extends HockeyleagueObjectImpl implements Arena {
	protected static final String ADDRESS_EDEFAULT = null;

	protected String address = ADDRESS_EDEFAULT;

	protected static final int CAPACITY_EDEFAULT = 0;

	protected int capacity = CAPACITY_EDEFAULT;

	protected ArenaImpl() {
		super();
	}

	protected EClass eStaticClass() {
		return HockeyleaguePackage.Literals.ARENA;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String newAddress) {
		String oldAddress = address;
		address = newAddress;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HockeyleaguePackage.ARENA__ADDRESS, oldAddress, address));
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int newCapacity) {
		int oldCapacity = capacity;
		capacity = newCapacity;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HockeyleaguePackage.ARENA__CAPACITY, oldCapacity, capacity));
	}

	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case HockeyleaguePackage.ARENA__ADDRESS:
				return getAddress();
			case HockeyleaguePackage.ARENA__CAPACITY:
				return new Integer(getCapacity());
		}
		return super.eGet(featureID, resolve, coreType);
	}

	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case HockeyleaguePackage.ARENA__ADDRESS:
				setAddress((String)newValue);
				return;
			case HockeyleaguePackage.ARENA__CAPACITY:
				setCapacity(((Integer)newValue).intValue());
				return;
		}
		super.eSet(featureID, newValue);
	}

	public void eUnset(int featureID) {
		switch (featureID) {
			case HockeyleaguePackage.ARENA__ADDRESS:
				setAddress(ADDRESS_EDEFAULT);
				return;
			case HockeyleaguePackage.ARENA__CAPACITY:
				setCapacity(CAPACITY_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case HockeyleaguePackage.ARENA__ADDRESS:
				return ADDRESS_EDEFAULT == null ? address != null : !ADDRESS_EDEFAULT.equals(address);
			case HockeyleaguePackage.ARENA__CAPACITY:
				return capacity != CAPACITY_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (address: "); //$NON-NLS-1$
		result.append(address);
		result.append(", capacity: "); //$NON-NLS-1$
		result.append(capacity);
		result.append(')');
		return result.toString();
	}

} //ArenaImpl
