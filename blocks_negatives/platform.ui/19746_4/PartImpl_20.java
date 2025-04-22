	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedOperationID(int baseOperationID, Class<?> baseClass) {
		if (baseClass == MPartSashContainerElement.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == MStackElement.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == MContribution.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == MContext.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == MUILabel.class) {
			switch (baseOperationID) {
				case UiPackageImpl.UI_LABEL___GET_LOCALIZED_LABEL: return BasicPackageImpl.PART___GET_LOCALIZED_LABEL;
				case UiPackageImpl.UI_LABEL___GET_LOCALIZED_TOOLTIP: return BasicPackageImpl.PART___GET_LOCALIZED_TOOLTIP;
				default: return -1;
			}
		}
		if (baseClass == MHandlerContainer.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == MDirtyable.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == MBindings.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == MWindowElement.class) {
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
			case BasicPackageImpl.PART___GET_LOCALIZED_DESCRIPTION:
				return getLocalizedDescription();
			case BasicPackageImpl.PART___GET_LOCALIZED_LABEL:
				return getLocalizedLabel();
			case BasicPackageImpl.PART___GET_LOCALIZED_TOOLTIP:
				return getLocalizedTooltip();
		}
		return super.eInvoke(operationID, arguments);
	}

