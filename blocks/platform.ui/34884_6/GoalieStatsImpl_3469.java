package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.impl;


import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.Goalie;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.GoalieStats;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.HockeyleaguePackage;

public class GoalieImpl extends PlayerImpl implements Goalie {
	protected EList goalieStats;

	protected GoalieImpl() {
		super();
	}

	protected EClass eStaticClass() {
		return HockeyleaguePackage.Literals.GOALIE;
	}

	public EList getGoalieStats() {
		if (goalieStats == null) {
			goalieStats = new EObjectContainmentEList(GoalieStats.class, this, HockeyleaguePackage.GOALIE__GOALIE_STATS);
		}
		return goalieStats;
	}

	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case HockeyleaguePackage.GOALIE__GOALIE_STATS:
				return ((InternalEList)getGoalieStats()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case HockeyleaguePackage.GOALIE__GOALIE_STATS:
				return getGoalieStats();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case HockeyleaguePackage.GOALIE__GOALIE_STATS:
				getGoalieStats().clear();
				getGoalieStats().addAll((Collection)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	public void eUnset(int featureID) {
		switch (featureID) {
			case HockeyleaguePackage.GOALIE__GOALIE_STATS:
				getGoalieStats().clear();
				return;
		}
		super.eUnset(featureID);
	}

	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case HockeyleaguePackage.GOALIE__GOALIE_STATS:
				return goalieStats != null && !goalieStats.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //GoalieImpl
