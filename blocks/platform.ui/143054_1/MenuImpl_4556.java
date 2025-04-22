		case MenuPackageImpl.MENU__CHILDREN:
			return getChildren();
		case MenuPackageImpl.MENU__SELECTED_ELEMENT:
			if (resolve)
				return getSelectedElement();
			return basicGetSelectedElement();
		case MenuPackageImpl.MENU__ENABLED:
			return isEnabled();
