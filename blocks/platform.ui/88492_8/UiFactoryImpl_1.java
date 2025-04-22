package org.eclipse.e4.ui.model.application.ui.impl;

import org.eclipse.e4.ui.model.application.MContribution;
import org.eclipse.e4.ui.model.application.impl.ApplicationPackageImpl;
import org.eclipse.e4.ui.model.application.ui.MImperativeExpression;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

public class ImperativeExpressionImpl extends ExpressionImpl implements MImperativeExpression {
	protected static final String CONTRIBUTION_URI_EDEFAULT = null;

	protected String contributionURI = CONTRIBUTION_URI_EDEFAULT;

	protected static final Object OBJECT_EDEFAULT = null;

	protected Object object = OBJECT_EDEFAULT;

	protected static final boolean TRACKING_EDEFAULT = false;

	protected boolean tracking = TRACKING_EDEFAULT;

	protected ImperativeExpressionImpl() {
		super();
	}

	@Override
	protected EClass eStaticClass() {
		return UiPackageImpl.Literals.IMPERATIVE_EXPRESSION;
	}

	public String getContributionURI() {
		return contributionURI;
	}

	public void setContributionURI(String newContributionURI) {
		String oldContributionURI = contributionURI;
		contributionURI = newContributionURI;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UiPackageImpl.IMPERATIVE_EXPRESSION__CONTRIBUTION_URI, oldContributionURI, contributionURI));
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object newObject) {
		Object oldObject = object;
		object = newObject;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UiPackageImpl.IMPERATIVE_EXPRESSION__OBJECT, oldObject, object));
	}

	public boolean isTracking() {
		return tracking;
	}

	public void setTracking(boolean newTracking) {
		boolean oldTracking = tracking;
		tracking = newTracking;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UiPackageImpl.IMPERATIVE_EXPRESSION__TRACKING, oldTracking, tracking));
	}

	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case UiPackageImpl.IMPERATIVE_EXPRESSION__CONTRIBUTION_URI:
			return getContributionURI();
		case UiPackageImpl.IMPERATIVE_EXPRESSION__OBJECT:
			return getObject();
		case UiPackageImpl.IMPERATIVE_EXPRESSION__TRACKING:
			return isTracking();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case UiPackageImpl.IMPERATIVE_EXPRESSION__CONTRIBUTION_URI:
			setContributionURI((String)newValue);
			return;
		case UiPackageImpl.IMPERATIVE_EXPRESSION__OBJECT:
			setObject(newValue);
			return;
		case UiPackageImpl.IMPERATIVE_EXPRESSION__TRACKING:
			setTracking((Boolean)newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case UiPackageImpl.IMPERATIVE_EXPRESSION__CONTRIBUTION_URI:
			setContributionURI(CONTRIBUTION_URI_EDEFAULT);
			return;
		case UiPackageImpl.IMPERATIVE_EXPRESSION__OBJECT:
			setObject(OBJECT_EDEFAULT);
			return;
		case UiPackageImpl.IMPERATIVE_EXPRESSION__TRACKING:
			setTracking(TRACKING_EDEFAULT);
			return;
		}
		super.eUnset(featureID);
	}

	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case UiPackageImpl.IMPERATIVE_EXPRESSION__CONTRIBUTION_URI:
			return CONTRIBUTION_URI_EDEFAULT == null ? contributionURI != null : !CONTRIBUTION_URI_EDEFAULT.equals(contributionURI);
		case UiPackageImpl.IMPERATIVE_EXPRESSION__OBJECT:
			return OBJECT_EDEFAULT == null ? object != null : !OBJECT_EDEFAULT.equals(object);
		case UiPackageImpl.IMPERATIVE_EXPRESSION__TRACKING:
			return tracking != TRACKING_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == MContribution.class) {
			switch (derivedFeatureID) {
			case UiPackageImpl.IMPERATIVE_EXPRESSION__CONTRIBUTION_URI: return ApplicationPackageImpl.CONTRIBUTION__CONTRIBUTION_URI;
			case UiPackageImpl.IMPERATIVE_EXPRESSION__OBJECT: return ApplicationPackageImpl.CONTRIBUTION__OBJECT;
			default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == MContribution.class) {
			switch (baseFeatureID) {
			case ApplicationPackageImpl.CONTRIBUTION__CONTRIBUTION_URI: return UiPackageImpl.IMPERATIVE_EXPRESSION__CONTRIBUTION_URI;
			case ApplicationPackageImpl.CONTRIBUTION__OBJECT: return UiPackageImpl.IMPERATIVE_EXPRESSION__OBJECT;
			default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (contributionURI: "); //$NON-NLS-1$
		result.append(contributionURI);
		result.append(", object: "); //$NON-NLS-1$
		result.append(object);
		result.append(", tracking: "); //$NON-NLS-1$
		result.append(tracking);
		result.append(')');
		return result.toString();
	}

} //ImperativeExpressionImpl
