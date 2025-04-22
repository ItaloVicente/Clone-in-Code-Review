package org.eclipse.e4.ui.workbench.internal.persistence.impl;

import org.eclipse.e4.ui.workbench.internal.persistence.PartMemento;
import org.eclipse.e4.ui.workbench.internal.persistence.PersistencePackage;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

public class PartMementoImpl extends MinimalEObjectImpl.Container implements PartMemento {

	protected static final String PART_ID_EDEFAULT = null;

	protected String partId = PART_ID_EDEFAULT;

	protected static final String MEMENTO_EDEFAULT = null;

	protected String memento = MEMENTO_EDEFAULT;

	protected PartMementoImpl() {
		super();
	}

	@Override
	protected EClass eStaticClass() {
		return PersistencePackage.Literals.PART_MEMENTO;
	}

	@Override
	public String getPartId() {
		return partId;
	}

	@Override
	public void setPartId(String newPartId) {
		String oldPartId = partId;
		partId = newPartId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PersistencePackage.PART_MEMENTO__PART_ID, oldPartId, partId));
	}

	@Override
	public String getMemento() {
		return memento;
	}

	@Override
	public void setMemento(String newMemento) {
		String oldMemento = memento;
		memento = newMemento;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PersistencePackage.PART_MEMENTO__MEMENTO, oldMemento, memento));
	}

	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PersistencePackage.PART_MEMENTO__PART_ID:
				return getPartId();
			case PersistencePackage.PART_MEMENTO__MEMENTO:
				return getMemento();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case PersistencePackage.PART_MEMENTO__PART_ID:
				setPartId((String)newValue);
				return;
			case PersistencePackage.PART_MEMENTO__MEMENTO:
				setMemento((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case PersistencePackage.PART_MEMENTO__PART_ID:
				setPartId(PART_ID_EDEFAULT);
				return;
			case PersistencePackage.PART_MEMENTO__MEMENTO:
				setMemento(MEMENTO_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case PersistencePackage.PART_MEMENTO__PART_ID:
				return PART_ID_EDEFAULT == null ? partId != null : !PART_ID_EDEFAULT.equals(partId);
			case PersistencePackage.PART_MEMENTO__MEMENTO:
				return MEMENTO_EDEFAULT == null ? memento != null : !MEMENTO_EDEFAULT.equals(memento);
		}
		return super.eIsSet(featureID);
	}

	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (partId: "); //$NON-NLS-1$
		result.append(partId);
		result.append(", memento: "); //$NON-NLS-1$
		result.append(memento);
		result.append(')');
		return result.toString();
	}

} // PartMementoImpl
