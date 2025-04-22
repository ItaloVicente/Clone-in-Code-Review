package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.impl;


import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.Defence;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.DefencePositionKind;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.HockeyleaguePackage;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.PlayerStats;

public class DefenceImpl extends PlayerImpl implements Defence {
	protected static final DefencePositionKind POSITION_EDEFAULT = DefencePositionKind.LEFT_DEFENCE_LITERAL;

	protected DefencePositionKind position = POSITION_EDEFAULT;

	protected EList playerStats;

	protected DefenceImpl() {
		super();
	}

	protected EClass eStaticClass() {
		return HockeyleaguePackage.Literals.DEFENCE;
	}

	public DefencePositionKind getPosition() {
		return position;
	}

	public void setPosition(DefencePositionKind newPosition) {
		DefencePositionKind oldPosition = position;
		position = newPosition == null ? POSITION_EDEFAULT : newPosition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HockeyleaguePackage.DEFENCE__POSITION, oldPosition, position));
	}

	public EList getPlayerStats() {
		if (playerStats == null) {
			playerStats = new EObjectContainmentEList(PlayerStats.class, this, HockeyleaguePackage.DEFENCE__PLAYER_STATS);
		}
		return playerStats;
	}

	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case HockeyleaguePackage.DEFENCE__PLAYER_STATS:
				return ((InternalEList)getPlayerStats()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case HockeyleaguePackage.DEFENCE__POSITION:
				return getPosition();
			case HockeyleaguePackage.DEFENCE__PLAYER_STATS:
				return getPlayerStats();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case HockeyleaguePackage.DEFENCE__POSITION:
				setPosition((DefencePositionKind)newValue);
				return;
			case HockeyleaguePackage.DEFENCE__PLAYER_STATS:
				getPlayerStats().clear();
				getPlayerStats().addAll((Collection)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	public void eUnset(int featureID) {
		switch (featureID) {
			case HockeyleaguePackage.DEFENCE__POSITION:
				setPosition(POSITION_EDEFAULT);
				return;
			case HockeyleaguePackage.DEFENCE__PLAYER_STATS:
				getPlayerStats().clear();
				return;
		}
		super.eUnset(featureID);
	}

	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case HockeyleaguePackage.DEFENCE__POSITION:
				return position != POSITION_EDEFAULT;
			case HockeyleaguePackage.DEFENCE__PLAYER_STATS:
				return playerStats != null && !playerStats.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (position: "); //$NON-NLS-1$
		result.append(position);
		result.append(')');
		return result.toString();
	}

} //DefenceImpl
