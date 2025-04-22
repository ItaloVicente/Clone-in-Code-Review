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
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.Arena;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.Defence;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.Forward;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.Goalie;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.HockeyleaguePackage;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.Team;

public class TeamImpl extends HockeyleagueObjectImpl implements Team {
	protected EList forwards;

	protected EList defencemen;

	protected EList goalies;

	protected Arena arena;

	protected TeamImpl() {
		super();
	}

	protected EClass eStaticClass() {
		return HockeyleaguePackage.Literals.TEAM;
	}

	public EList getForwards() {
		if (forwards == null) {
			forwards = new EObjectContainmentEList(Forward.class, this, HockeyleaguePackage.TEAM__FORWARDS);
		}
		return forwards;
	}

	public EList getDefencemen() {
		if (defencemen == null) {
			defencemen = new EObjectContainmentEList(Defence.class, this, HockeyleaguePackage.TEAM__DEFENCEMEN);
		}
		return defencemen;
	}

	public EList getGoalies() {
		if (goalies == null) {
			goalies = new EObjectContainmentEList(Goalie.class, this, HockeyleaguePackage.TEAM__GOALIES);
		}
		return goalies;
	}

	public Arena getArena() {
		return arena;
	}

	public NotificationChain basicSetArena(Arena newArena, NotificationChain msgs) {
		Arena oldArena = arena;
		arena = newArena;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, HockeyleaguePackage.TEAM__ARENA, oldArena, newArena);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	public void setArena(Arena newArena) {
		if (newArena != arena) {
			NotificationChain msgs = null;
			if (arena != null)
				msgs = ((InternalEObject)arena).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - HockeyleaguePackage.TEAM__ARENA, null, msgs);
			if (newArena != null)
				msgs = ((InternalEObject)newArena).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - HockeyleaguePackage.TEAM__ARENA, null, msgs);
			msgs = basicSetArena(newArena, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HockeyleaguePackage.TEAM__ARENA, newArena, newArena));
	}

	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case HockeyleaguePackage.TEAM__FORWARDS:
				return ((InternalEList)getForwards()).basicRemove(otherEnd, msgs);
			case HockeyleaguePackage.TEAM__DEFENCEMEN:
				return ((InternalEList)getDefencemen()).basicRemove(otherEnd, msgs);
			case HockeyleaguePackage.TEAM__GOALIES:
				return ((InternalEList)getGoalies()).basicRemove(otherEnd, msgs);
			case HockeyleaguePackage.TEAM__ARENA:
				return basicSetArena(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case HockeyleaguePackage.TEAM__FORWARDS:
				return getForwards();
			case HockeyleaguePackage.TEAM__DEFENCEMEN:
				return getDefencemen();
			case HockeyleaguePackage.TEAM__GOALIES:
				return getGoalies();
			case HockeyleaguePackage.TEAM__ARENA:
				return getArena();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case HockeyleaguePackage.TEAM__FORWARDS:
				getForwards().clear();
				getForwards().addAll((Collection)newValue);
				return;
			case HockeyleaguePackage.TEAM__DEFENCEMEN:
				getDefencemen().clear();
				getDefencemen().addAll((Collection)newValue);
				return;
			case HockeyleaguePackage.TEAM__GOALIES:
				getGoalies().clear();
				getGoalies().addAll((Collection)newValue);
				return;
			case HockeyleaguePackage.TEAM__ARENA:
				setArena((Arena)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	public void eUnset(int featureID) {
		switch (featureID) {
			case HockeyleaguePackage.TEAM__FORWARDS:
				getForwards().clear();
				return;
			case HockeyleaguePackage.TEAM__DEFENCEMEN:
				getDefencemen().clear();
				return;
			case HockeyleaguePackage.TEAM__GOALIES:
				getGoalies().clear();
				return;
			case HockeyleaguePackage.TEAM__ARENA:
				setArena((Arena)null);
				return;
		}
		super.eUnset(featureID);
	}

	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case HockeyleaguePackage.TEAM__FORWARDS:
				return forwards != null && !forwards.isEmpty();
			case HockeyleaguePackage.TEAM__DEFENCEMEN:
				return defencemen != null && !defencemen.isEmpty();
			case HockeyleaguePackage.TEAM__GOALIES:
				return goalies != null && !goalies.isEmpty();
			case HockeyleaguePackage.TEAM__ARENA:
				return arena != null;
		}
		return super.eIsSet(featureID);
	}

} //TeamImpl
