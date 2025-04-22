		if (baseClass == MLocalizable.class) {
			switch (baseOperationID) {
				case UiPackageImpl.LOCALIZABLE___UPDATE_LOCALIZATION: return MenuPackageImpl.MENU_ITEM___UPDATE_LOCALIZATION;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == MUIElement.class) {
			switch (baseOperationID) {
				case UiPackageImpl.UI_ELEMENT___UPDATE_LOCALIZATION: return MenuPackageImpl.MENU_ITEM___UPDATE_LOCALIZATION;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == MUILabel.class) {
			switch (baseOperationID) {
				case UiPackageImpl.UI_LABEL___UPDATE_LOCALIZATION: return MenuPackageImpl.MENU_ITEM___UPDATE_LOCALIZATION;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == MItem.class) {
			switch (baseOperationID) {
				case MenuPackageImpl.ITEM___UPDATE_LOCALIZATION: return MenuPackageImpl.MENU_ITEM___UPDATE_LOCALIZATION;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
