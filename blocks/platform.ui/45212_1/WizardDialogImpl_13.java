	@Override
	public List<MWizardElement> getChildren() {
		if (children == null) {
			children = new EObjectContainmentWithInverseEList<MWizardElement>(MWizardElement.class, this, BasicPackageImpl.WIZARD_DIALOG__CHILDREN, UiPackageImpl.UI_ELEMENT__PARENT) { private static final long serialVersionUID = 1L; @Override public Class<?> getInverseFeatureClass() { return MUIElement.class; } };
		}
		return children;
	}

	@Override
	public void setSelectedElement(MWizardElement newSelectedElement) {
		super.setSelectedElement(newSelectedElement);
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String newLabel) {
		String oldLabel = label;
		label = newLabel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasicPackageImpl.WIZARD_DIALOG__LABEL, oldLabel, label));
	}

	public String getIconURI() {
		return iconURI;
	}

	public void setIconURI(String newIconURI) {
		String oldIconURI = iconURI;
		iconURI = newIconURI;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasicPackageImpl.WIZARD_DIALOG__ICON_URI, oldIconURI, iconURI));
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String newTooltip) {
		String oldTooltip = tooltip;
		tooltip = newTooltip;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasicPackageImpl.WIZARD_DIALOG__TOOLTIP, oldTooltip, tooltip));
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
			case BasicPackageImpl.WIZARD_DIALOG__LABEL:
				return getLabel();
			case BasicPackageImpl.WIZARD_DIALOG__ICON_URI:
				return getIconURI();
			case BasicPackageImpl.WIZARD_DIALOG__TOOLTIP:
				return getTooltip();
			case BasicPackageImpl.WIZARD_DIALOG__LOCALIZED_LABEL:
				return getLocalizedLabel();
			case BasicPackageImpl.WIZARD_DIALOG__LOCALIZED_TOOLTIP:
				return getLocalizedTooltip();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case BasicPackageImpl.WIZARD_DIALOG__LABEL:
				setLabel((String)newValue);
				return;
			case BasicPackageImpl.WIZARD_DIALOG__ICON_URI:
				setIconURI((String)newValue);
				return;
			case BasicPackageImpl.WIZARD_DIALOG__TOOLTIP:
				setTooltip((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case BasicPackageImpl.WIZARD_DIALOG__LABEL:
				setLabel(LABEL_EDEFAULT);
				return;
			case BasicPackageImpl.WIZARD_DIALOG__ICON_URI:
				setIconURI(ICON_URI_EDEFAULT);
				return;
			case BasicPackageImpl.WIZARD_DIALOG__TOOLTIP:
				setTooltip(TOOLTIP_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case BasicPackageImpl.WIZARD_DIALOG__LABEL:
				return LABEL_EDEFAULT == null ? label != null : !LABEL_EDEFAULT.equals(label);
			case BasicPackageImpl.WIZARD_DIALOG__ICON_URI:
				return ICON_URI_EDEFAULT == null ? iconURI != null : !ICON_URI_EDEFAULT.equals(iconURI);
			case BasicPackageImpl.WIZARD_DIALOG__TOOLTIP:
				return TOOLTIP_EDEFAULT == null ? tooltip != null : !TOOLTIP_EDEFAULT.equals(tooltip);
			case BasicPackageImpl.WIZARD_DIALOG__LOCALIZED_LABEL:
				return LOCALIZED_LABEL_EDEFAULT == null ? getLocalizedLabel() != null : !LOCALIZED_LABEL_EDEFAULT.equals(getLocalizedLabel());
			case BasicPackageImpl.WIZARD_DIALOG__LOCALIZED_TOOLTIP:
				return LOCALIZED_TOOLTIP_EDEFAULT == null ? getLocalizedTooltip() != null : !LOCALIZED_TOOLTIP_EDEFAULT.equals(getLocalizedTooltip());
		}
		return super.eIsSet(featureID);
	}

	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == MUILabel.class) {
			switch (derivedFeatureID) {
				case BasicPackageImpl.WIZARD_DIALOG__LABEL: return UiPackageImpl.UI_LABEL__LABEL;
				case BasicPackageImpl.WIZARD_DIALOG__ICON_URI: return UiPackageImpl.UI_LABEL__ICON_URI;
				case BasicPackageImpl.WIZARD_DIALOG__TOOLTIP: return UiPackageImpl.UI_LABEL__TOOLTIP;
				case BasicPackageImpl.WIZARD_DIALOG__LOCALIZED_LABEL: return UiPackageImpl.UI_LABEL__LOCALIZED_LABEL;
				case BasicPackageImpl.WIZARD_DIALOG__LOCALIZED_TOOLTIP: return UiPackageImpl.UI_LABEL__LOCALIZED_TOOLTIP;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == MUILabel.class) {
			switch (baseFeatureID) {
				case UiPackageImpl.UI_LABEL__LABEL: return BasicPackageImpl.WIZARD_DIALOG__LABEL;
				case UiPackageImpl.UI_LABEL__ICON_URI: return BasicPackageImpl.WIZARD_DIALOG__ICON_URI;
				case UiPackageImpl.UI_LABEL__TOOLTIP: return BasicPackageImpl.WIZARD_DIALOG__TOOLTIP;
				case UiPackageImpl.UI_LABEL__LOCALIZED_LABEL: return BasicPackageImpl.WIZARD_DIALOG__LOCALIZED_LABEL;
				case UiPackageImpl.UI_LABEL__LOCALIZED_TOOLTIP: return BasicPackageImpl.WIZARD_DIALOG__LOCALIZED_TOOLTIP;
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

