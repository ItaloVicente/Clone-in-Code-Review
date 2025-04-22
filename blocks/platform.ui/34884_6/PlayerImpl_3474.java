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
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.HockeyleaguePackage;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.League;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.Team;

public class LeagueImpl extends HockeyleagueObjectImpl implements League {
	protected static final String HEADOFFICE_EDEFAULT = null;

	protected String headoffice = HEADOFFICE_EDEFAULT;

	protected EList teams;

	protected LeagueImpl() {
		super();
	}

	protected EClass eStaticClass() {
		return HockeyleaguePackage.Literals.LEAGUE;
	}

	public String getHeadoffice() {
		return headoffice;
	}

	public void setHeadoffice(String newHeadoffice) {
		String oldHeadoffice = headoffice;
		headoffice = newHeadoffice;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HockeyleaguePackage.LEAGUE__HEADOFFICE, oldHeadoffice, headoffice));
	}

	public EList getTeams() {
		if (teams == null) {
			teams = new EObjectContainmentEList(Team.class, this, HockeyleaguePackage.LEAGUE__TEAMS);
		}
		return teams;
	}

	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case HockeyleaguePackage.LEAGUE__TEAMS:
				return ((InternalEList)getTeams()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case HockeyleaguePackage.LEAGUE__HEADOFFICE:
				return getHeadoffice();
			case HockeyleaguePackage.LEAGUE__TEAMS:
				return getTeams();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case HockeyleaguePackage.LEAGUE__HEADOFFICE:
				setHeadoffice((String)newValue);
				return;
			case HockeyleaguePackage.LEAGUE__TEAMS:
				getTeams().clear();
				getTeams().addAll((Collection)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	public void eUnset(int featureID) {
		switch (featureID) {
			case HockeyleaguePackage.LEAGUE__HEADOFFICE:
				setHeadoffice(HEADOFFICE_EDEFAULT);
				return;
			case HockeyleaguePackage.LEAGUE__TEAMS:
				getTeams().clear();
				return;
		}
		super.eUnset(featureID);
	}

	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case HockeyleaguePackage.LEAGUE__HEADOFFICE:
				return HEADOFFICE_EDEFAULT == null ? headoffice != null : !HEADOFFICE_EDEFAULT.equals(headoffice);
			case HockeyleaguePackage.LEAGUE__TEAMS:
				return teams != null && !teams.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (headoffice: "); //$NON-NLS-1$
		result.append(headoffice);
		result.append(')');
		return result.toString();
	}

} //LeagueImpl
