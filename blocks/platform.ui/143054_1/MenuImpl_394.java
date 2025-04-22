		case MenuPackageImpl.MENU__CHILDREN:
			getChildren().clear();
			return;
		case MenuPackageImpl.MENU__SELECTED_ELEMENT:
			setSelectedElement((MMenuElement) null);
			return;
		case MenuPackageImpl.MENU__ENABLED:
			setEnabled(ENABLED_EDEFAULT);
			return;
