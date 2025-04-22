package org.eclipse.e4.emf.xpath.test.model.xpathtest.impl;

import java.util.Collection;

import org.eclipse.e4.emf.xpath.test.model.xpathtest.Node;
import org.eclipse.e4.emf.xpath.test.model.xpathtest.Root;
import org.eclipse.e4.emf.xpath.test.model.xpathtest.XpathtestPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

public class RootImpl extends EObjectImpl implements Root {
	protected EList<Node> nodes;

	protected static final String ID_EDEFAULT = null;

	protected String id = ID_EDEFAULT;

	protected RootImpl() {
		super();
	}

	@Override
	protected EClass eStaticClass() {
		return XpathtestPackage.Literals.ROOT;
	}

	public EList<Node> getNodes() {
		if (nodes == null) {
			nodes = new EObjectContainmentEList<Node>(Node.class, this, XpathtestPackage.ROOT__NODES);
		}
		return nodes;
	}

	public String getId() {
		return id;
	}

	public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, XpathtestPackage.ROOT__ID, oldId, id));
	}

	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case XpathtestPackage.ROOT__NODES:
				return ((InternalEList<?>)getNodes()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case XpathtestPackage.ROOT__NODES:
				return getNodes();
			case XpathtestPackage.ROOT__ID:
				return getId();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case XpathtestPackage.ROOT__NODES:
				getNodes().clear();
				getNodes().addAll((Collection<? extends Node>)newValue);
				return;
			case XpathtestPackage.ROOT__ID:
				setId((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case XpathtestPackage.ROOT__NODES:
				getNodes().clear();
				return;
			case XpathtestPackage.ROOT__ID:
				setId(ID_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case XpathtestPackage.ROOT__NODES:
				return nodes != null && !nodes.isEmpty();
			case XpathtestPackage.ROOT__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
		}
		return super.eIsSet(featureID);
	}

	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (id: ");
		result.append(id);
		result.append(')');
		return result.toString();
	}

} //RootImpl
