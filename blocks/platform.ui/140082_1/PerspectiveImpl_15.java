	@Override
	public int eDerivedOperationID(int baseOperationID, Class<?> baseClass) {
		if (baseClass == MLocalizable.class) {
			switch (baseOperationID) {
				case UiPackageImpl.LOCALIZABLE___UPDATE_LOCALIZATION: return AdvancedPackageImpl.PERSPECTIVE___UPDATE_LOCALIZATION;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == MUIElement.class) {
			switch (baseOperationID) {
				case UiPackageImpl.UI_ELEMENT___UPDATE_LOCALIZATION: return AdvancedPackageImpl.PERSPECTIVE___UPDATE_LOCALIZATION;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == MUILabel.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == MContext.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == MHandlerContainer.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == MBindings.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		return super.eDerivedOperationID(baseOperationID, baseClass);
	}

	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case AdvancedPackageImpl.PERSPECTIVE___UPDATE_LOCALIZATION:
				updateLocalization();
				return null;
		}
		return super.eInvoke(operationID, arguments);
	}

