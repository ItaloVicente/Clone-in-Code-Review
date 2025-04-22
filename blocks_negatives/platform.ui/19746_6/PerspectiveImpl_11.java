	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedOperationID(int baseOperationID, Class<?> baseClass) {
		if (baseClass == MUILabel.class) {
			switch (baseOperationID) {
				case UiPackageImpl.UI_LABEL___GET_LOCALIZED_LABEL: return AdvancedPackageImpl.PERSPECTIVE___GET_LOCALIZED_LABEL;
				case UiPackageImpl.UI_LABEL___GET_LOCALIZED_TOOLTIP: return AdvancedPackageImpl.PERSPECTIVE___GET_LOCALIZED_TOOLTIP;
				default: return -1;
			}
		}
		if (baseClass == MContext.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		return super.eDerivedOperationID(baseOperationID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case AdvancedPackageImpl.PERSPECTIVE___GET_LOCALIZED_LABEL:
				return getLocalizedLabel();
			case AdvancedPackageImpl.PERSPECTIVE___GET_LOCALIZED_TOOLTIP:
				return getLocalizedTooltip();
		}
		return super.eInvoke(operationID, arguments);
	}

