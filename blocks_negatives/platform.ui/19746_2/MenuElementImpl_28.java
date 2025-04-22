	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedOperationID(int baseOperationID, Class<?> baseClass) {
		if (baseClass == MUILabel.class) {
			switch (baseOperationID) {
				case UiPackageImpl.UI_LABEL___GET_LOCALIZED_LABEL: return MenuPackageImpl.MENU_ELEMENT___GET_LOCALIZED_LABEL;
				case UiPackageImpl.UI_LABEL___GET_LOCALIZED_TOOLTIP: return MenuPackageImpl.MENU_ELEMENT___GET_LOCALIZED_TOOLTIP;
				default: return -1;
			}
		}
		return super.eDerivedOperationID(baseOperationID, baseClass);
	}

