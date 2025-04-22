package org.eclipse.e4.emf.xpath.test.model.xpathtest.impl;

import org.eclipse.e4.emf.xpath.test.model.xpathtest.MenuElement;
import org.eclipse.e4.emf.xpath.test.model.xpathtest.XpathtestPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

public class MenuElementImpl extends EObjectImpl implements MenuElement {
	protected static final String ID_EDEFAULT = null;

	protected String id = ID_EDEFAULT;

	protected static final String LABEL_EDEFAULT = null;

	protected String label = LABEL_EDEFAULT;

	protected MenuElementImpl() {
		super();
	}

	@Override
	protected EClass eStaticClass() {
		return XpathtestPackage.Literals.MENU_ELEMENT;
	}

	public String getId() {
		return id;
	}

	public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, XpathtestPackage.MENU_ELEMENT__ID, oldId, id));
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String newLabel) {
		String oldLabel = label;
		label = newLabel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, XpathtestPackage.MENU_ELEMENT__LABEL, oldLabel, label));
	}

	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case XpathtestPackage.MENU_ELEMENT__ID:
				return getId();
			case XpathtestPackage.MENU_ELEMENT__LABEL:
				return getLabel();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case XpathtestPackage.MENU_ELEMENT__ID:
				setId((String)newValue);
				return;
			case XpathtestPackage.MENU_ELEMENT__LABEL:
				setLabel((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case XpathtestPackage.MENU_ELEMENT__ID:
				setId(ID_EDEFAULT);
				return;
			case XpathtestPackage.MENU_ELEMENT__LABEL:
				setLabel(LABEL_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case XpathtestPackage.MENU_ELEMENT__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case XpathtestPackage.MENU_ELEMENT__LABEL:
				return LABEL_EDEFAULT == null ? label != null : !LABEL_EDEFAULT.equals(label);
		}
		return super.eIsSet(featureID);
	}

	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (id: ");
		result.append(id);
		result.append(", label: ");
		result.append(label);
		result.append(')');
		return result.toString();
	}

} //MenuElementImpl
