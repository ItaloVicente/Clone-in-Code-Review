package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.impl;


import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.HeightKind;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.HockeyleaguePackage;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.Player;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.ShotKind;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.WeightKind;

public abstract class PlayerImpl extends HockeyleagueObjectImpl implements Player {
	protected static final String BIRTHPLACE_EDEFAULT = null;

	protected String birthplace = BIRTHPLACE_EDEFAULT;

	protected static final int NUMBER_EDEFAULT = 0;

	protected int number = NUMBER_EDEFAULT;

	protected static final HeightKind HEIGHT_MESUREMENT_EDEFAULT = HeightKind.INCHES_LITERAL;

	protected HeightKind heightMesurement = HEIGHT_MESUREMENT_EDEFAULT;

	protected static final int HEIGHT_VALUE_EDEFAULT = 0;

	protected int heightValue = HEIGHT_VALUE_EDEFAULT;

	protected static final WeightKind WEIGHT_MESUREMENT_EDEFAULT = WeightKind.POUNDS_LITERAL;

	protected WeightKind weightMesurement = WEIGHT_MESUREMENT_EDEFAULT;

	protected static final int WEIGHT_VALUE_EDEFAULT = 0;

	protected int weightValue = WEIGHT_VALUE_EDEFAULT;

	protected static final ShotKind SHOT_EDEFAULT = ShotKind.LEFT_LITERAL;

	protected ShotKind shot = SHOT_EDEFAULT;

	protected static final String BIRTHDATE_EDEFAULT = null;

	protected String birthdate = BIRTHDATE_EDEFAULT;

	protected PlayerImpl() {
		super();
	}

	protected EClass eStaticClass() {
		return HockeyleaguePackage.Literals.PLAYER;
	}

	public String getBirthplace() {
		return birthplace;
	}

	public void setBirthplace(String newBirthplace) {
		String oldBirthplace = birthplace;
		birthplace = newBirthplace;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HockeyleaguePackage.PLAYER__BIRTHPLACE, oldBirthplace, birthplace));
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int newNumber) {
		int oldNumber = number;
		number = newNumber;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HockeyleaguePackage.PLAYER__NUMBER, oldNumber, number));
	}

	public HeightKind getHeightMesurement() {
		return heightMesurement;
	}

	public void setHeightMesurement(HeightKind newHeightMesurement) {
		HeightKind oldHeightMesurement = heightMesurement;
		heightMesurement = newHeightMesurement == null ? HEIGHT_MESUREMENT_EDEFAULT : newHeightMesurement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HockeyleaguePackage.PLAYER__HEIGHT_MESUREMENT, oldHeightMesurement, heightMesurement));
	}

	public int getHeightValue() {
		return heightValue;
	}

	public void setHeightValue(int newHeightValue) {
		int oldHeightValue = heightValue;
		heightValue = newHeightValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HockeyleaguePackage.PLAYER__HEIGHT_VALUE, oldHeightValue, heightValue));
	}

	public WeightKind getWeightMesurement() {
		return weightMesurement;
	}

	public void setWeightMesurement(WeightKind newWeightMesurement) {
		WeightKind oldWeightMesurement = weightMesurement;
		weightMesurement = newWeightMesurement == null ? WEIGHT_MESUREMENT_EDEFAULT : newWeightMesurement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HockeyleaguePackage.PLAYER__WEIGHT_MESUREMENT, oldWeightMesurement, weightMesurement));
	}

	public int getWeightValue() {
		return weightValue;
	}

	public void setWeightValue(int newWeightValue) {
		int oldWeightValue = weightValue;
		weightValue = newWeightValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HockeyleaguePackage.PLAYER__WEIGHT_VALUE, oldWeightValue, weightValue));
	}

	public ShotKind getShot() {
		return shot;
	}

	public void setShot(ShotKind newShot) {
		ShotKind oldShot = shot;
		shot = newShot == null ? SHOT_EDEFAULT : newShot;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HockeyleaguePackage.PLAYER__SHOT, oldShot, shot));
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String newBirthdate) {
		String oldBirthdate = birthdate;
		birthdate = newBirthdate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HockeyleaguePackage.PLAYER__BIRTHDATE, oldBirthdate, birthdate));
	}

	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case HockeyleaguePackage.PLAYER__BIRTHPLACE:
				return getBirthplace();
			case HockeyleaguePackage.PLAYER__NUMBER:
				return new Integer(getNumber());
			case HockeyleaguePackage.PLAYER__HEIGHT_MESUREMENT:
				return getHeightMesurement();
			case HockeyleaguePackage.PLAYER__HEIGHT_VALUE:
				return new Integer(getHeightValue());
			case HockeyleaguePackage.PLAYER__WEIGHT_MESUREMENT:
				return getWeightMesurement();
			case HockeyleaguePackage.PLAYER__WEIGHT_VALUE:
				return new Integer(getWeightValue());
			case HockeyleaguePackage.PLAYER__SHOT:
				return getShot();
			case HockeyleaguePackage.PLAYER__BIRTHDATE:
				return getBirthdate();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case HockeyleaguePackage.PLAYER__BIRTHPLACE:
				setBirthplace((String)newValue);
				return;
			case HockeyleaguePackage.PLAYER__NUMBER:
				setNumber(((Integer)newValue).intValue());
				return;
			case HockeyleaguePackage.PLAYER__HEIGHT_MESUREMENT:
				setHeightMesurement((HeightKind)newValue);
				return;
			case HockeyleaguePackage.PLAYER__HEIGHT_VALUE:
				setHeightValue(((Integer)newValue).intValue());
				return;
			case HockeyleaguePackage.PLAYER__WEIGHT_MESUREMENT:
				setWeightMesurement((WeightKind)newValue);
				return;
			case HockeyleaguePackage.PLAYER__WEIGHT_VALUE:
				setWeightValue(((Integer)newValue).intValue());
				return;
			case HockeyleaguePackage.PLAYER__SHOT:
				setShot((ShotKind)newValue);
				return;
			case HockeyleaguePackage.PLAYER__BIRTHDATE:
				setBirthdate((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	public void eUnset(int featureID) {
		switch (featureID) {
			case HockeyleaguePackage.PLAYER__BIRTHPLACE:
				setBirthplace(BIRTHPLACE_EDEFAULT);
				return;
			case HockeyleaguePackage.PLAYER__NUMBER:
				setNumber(NUMBER_EDEFAULT);
				return;
			case HockeyleaguePackage.PLAYER__HEIGHT_MESUREMENT:
				setHeightMesurement(HEIGHT_MESUREMENT_EDEFAULT);
				return;
			case HockeyleaguePackage.PLAYER__HEIGHT_VALUE:
				setHeightValue(HEIGHT_VALUE_EDEFAULT);
				return;
			case HockeyleaguePackage.PLAYER__WEIGHT_MESUREMENT:
				setWeightMesurement(WEIGHT_MESUREMENT_EDEFAULT);
				return;
			case HockeyleaguePackage.PLAYER__WEIGHT_VALUE:
				setWeightValue(WEIGHT_VALUE_EDEFAULT);
				return;
			case HockeyleaguePackage.PLAYER__SHOT:
				setShot(SHOT_EDEFAULT);
				return;
			case HockeyleaguePackage.PLAYER__BIRTHDATE:
				setBirthdate(BIRTHDATE_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case HockeyleaguePackage.PLAYER__BIRTHPLACE:
				return BIRTHPLACE_EDEFAULT == null ? birthplace != null : !BIRTHPLACE_EDEFAULT.equals(birthplace);
			case HockeyleaguePackage.PLAYER__NUMBER:
				return number != NUMBER_EDEFAULT;
			case HockeyleaguePackage.PLAYER__HEIGHT_MESUREMENT:
				return heightMesurement != HEIGHT_MESUREMENT_EDEFAULT;
			case HockeyleaguePackage.PLAYER__HEIGHT_VALUE:
				return heightValue != HEIGHT_VALUE_EDEFAULT;
			case HockeyleaguePackage.PLAYER__WEIGHT_MESUREMENT:
				return weightMesurement != WEIGHT_MESUREMENT_EDEFAULT;
			case HockeyleaguePackage.PLAYER__WEIGHT_VALUE:
				return weightValue != WEIGHT_VALUE_EDEFAULT;
			case HockeyleaguePackage.PLAYER__SHOT:
				return shot != SHOT_EDEFAULT;
			case HockeyleaguePackage.PLAYER__BIRTHDATE:
				return BIRTHDATE_EDEFAULT == null ? birthdate != null : !BIRTHDATE_EDEFAULT.equals(birthdate);
		}
		return super.eIsSet(featureID);
	}

	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (birthplace: "); //$NON-NLS-1$
		result.append(birthplace);
		result.append(", number: "); //$NON-NLS-1$
		result.append(number);
		result.append(", heightMesurement: "); //$NON-NLS-1$
		result.append(heightMesurement);
		result.append(", heightValue: "); //$NON-NLS-1$
		result.append(heightValue);
		result.append(", weightMesurement: "); //$NON-NLS-1$
		result.append(weightMesurement);
		result.append(", weightValue: "); //$NON-NLS-1$
		result.append(weightValue);
		result.append(", shot: "); //$NON-NLS-1$
		result.append(shot);
		result.append(", birthdate: "); //$NON-NLS-1$
		result.append(birthdate);
		result.append(')');
		return result.toString();
	}

} //PlayerImpl
