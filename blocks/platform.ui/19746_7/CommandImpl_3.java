	@Override
	public int eDerivedOperationID(int baseOperationID, Class<?> baseClass) {
		if (baseClass == MLocalizable.class) {
			switch (baseOperationID) {
				case UiPackageImpl.LOCALIZABLE___UPDATE_LOCALIZATION: return CommandsPackageImpl.COMMAND___UPDATE_LOCALIZATION;
				default: return -1;
			}
		}
		return super.eDerivedOperationID(baseOperationID, baseClass);
	}

