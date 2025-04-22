package org.eclipse.e4.ui.model.application.ui.basic.impl;

import org.eclipse.e4.ui.model.application.ui.MUILabel;
import org.eclipse.e4.ui.model.application.ui.basic.MFrameElement;
import org.eclipse.e4.ui.model.application.ui.basic.MStackElement;
import org.eclipse.e4.ui.model.application.ui.basic.MWizard;
import org.eclipse.e4.ui.model.application.ui.impl.GenericStackImpl;
import org.eclipse.e4.ui.model.application.ui.impl.UiPackageImpl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

public class WizardImpl extends GenericStackImpl<MStackElement> implements MWizard {
	protected static final String LABEL_EDEFAULT = null;
	protected String label = LABEL_EDEFAULT;
	protected static final String ICON_URI_EDEFAULT = null;
	protected String iconURI = ICON_URI_EDEFAULT;
	protected static final String TOOLTIP_EDEFAULT = null;
	protected String tooltip = TOOLTIP_EDEFAULT;
	protected static final String LOCALIZED_LABEL_EDEFAULT = ""; //$NON-NLS-1$
	protected static final String LOCALIZED_TOOLTIP_EDEFAULT = ""; //$NON-NLS-1$

	protected WizardImpl() {
		super();
	}

	@Override
	protected EClass eStaticClass() {
		return BasicPackageImpl.Literals.WIZARD;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String newLabel) {
		String oldLabel = label;
		label = newLabel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasicPackageImpl.WIZARD__LABEL, oldLabel, label));
	}

	public String getIconURI() {
		return iconURI;
	}

	public void setIconURI(String newIconURI) {
		String oldIconURI = iconURI;
		iconURI = newIconURI;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasicPackageImpl.WIZARD__ICON_URI, oldIconURI, iconURI));
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String newTooltip) {
		String oldTooltip = tooltip;
		tooltip = newTooltip;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasicPackageImpl.WIZARD__TOOLTIP, oldTooltip, tooltip));
	}

	public String getLocalizedLabel() {
		throw new UnsupportedOperationException();
	}

	public String getLocalizedTooltip() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BasicPackageImpl.WIZARD__LABEL:
				return getLabel();
			case BasicPackageImpl.WIZARD__ICON_URI:
				return getIconURI();
			case BasicPackageImpl.WIZARD__TOOLTIP:
				return getTooltip();
			case BasicPackageImpl.WIZARD__LOCALIZED_LABEL:
				return getLocalizedLabel();
			case BasicPackageImpl.WIZARD__LOCALIZED_TOOLTIP:
				return getLocalizedTooltip();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case BasicPackageImpl.WIZARD__LABEL:
				setLabel((String)newValue);
				return;
			case BasicPackageImpl.WIZARD__ICON_URI:
				setIconURI((String)newValue);
				return;
			case BasicPackageImpl.WIZARD__TOOLTIP:
				setTooltip((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case BasicPackageImpl.WIZARD__LABEL:
				setLabel(LABEL_EDEFAULT);
				return;
			case BasicPackageImpl.WIZARD__ICON_URI:
				setIconURI(ICON_URI_EDEFAULT);
				return;
			case BasicPackageImpl.WIZARD__TOOLTIP:
				setTooltip(TOOLTIP_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case BasicPackageImpl.WIZARD__LABEL:
				return LABEL_EDEFAULT == null ? label != null : !LABEL_EDEFAULT.equals(label);
			case BasicPackageImpl.WIZARD__ICON_URI:
				return ICON_URI_EDEFAULT == null ? iconURI != null : !ICON_URI_EDEFAULT.equals(iconURI);
			case BasicPackageImpl.WIZARD__TOOLTIP:
				return TOOLTIP_EDEFAULT == null ? tooltip != null : !TOOLTIP_EDEFAULT.equals(tooltip);
			case BasicPackageImpl.WIZARD__LOCALIZED_LABEL:
				return LOCALIZED_LABEL_EDEFAULT == null ? getLocalizedLabel() != null : !LOCALIZED_LABEL_EDEFAULT.equals(getLocalizedLabel());
			case BasicPackageImpl.WIZARD__LOCALIZED_TOOLTIP:
				return LOCALIZED_TOOLTIP_EDEFAULT == null ? getLocalizedTooltip() != null : !LOCALIZED_TOOLTIP_EDEFAULT.equals(getLocalizedTooltip());
		}
		return super.eIsSet(featureID);
	}

	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == MFrameElement.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == MUILabel.class) {
			switch (derivedFeatureID) {
				case BasicPackageImpl.WIZARD__LABEL: return UiPackageImpl.UI_LABEL__LABEL;
				case BasicPackageImpl.WIZARD__ICON_URI: return UiPackageImpl.UI_LABEL__ICON_URI;
				case BasicPackageImpl.WIZARD__TOOLTIP: return UiPackageImpl.UI_LABEL__TOOLTIP;
				case BasicPackageImpl.WIZARD__LOCALIZED_LABEL: return UiPackageImpl.UI_LABEL__LOCALIZED_LABEL;
				case BasicPackageImpl.WIZARD__LOCALIZED_TOOLTIP: return UiPackageImpl.UI_LABEL__LOCALIZED_TOOLTIP;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == MFrameElement.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == MUILabel.class) {
			switch (baseFeatureID) {
				case UiPackageImpl.UI_LABEL__LABEL: return BasicPackageImpl.WIZARD__LABEL;
				case UiPackageImpl.UI_LABEL__ICON_URI: return BasicPackageImpl.WIZARD__ICON_URI;
				case UiPackageImpl.UI_LABEL__TOOLTIP: return BasicPackageImpl.WIZARD__TOOLTIP;
				case UiPackageImpl.UI_LABEL__LOCALIZED_LABEL: return BasicPackageImpl.WIZARD__LOCALIZED_LABEL;
				case UiPackageImpl.UI_LABEL__LOCALIZED_TOOLTIP: return BasicPackageImpl.WIZARD__LOCALIZED_TOOLTIP;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (label: "); //$NON-NLS-1$
		result.append(label);
		result.append(", iconURI: "); //$NON-NLS-1$
		result.append(iconURI);
		result.append(", tooltip: "); //$NON-NLS-1$
		result.append(tooltip);
		result.append(')');
		return result.toString();
	}

} //WizardImpl
