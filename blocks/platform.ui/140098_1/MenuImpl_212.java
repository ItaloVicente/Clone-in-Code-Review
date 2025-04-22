		case MenuPackageImpl.MENU__CHILDREN:
			return children != null && !children.isEmpty();
		case MenuPackageImpl.MENU__SELECTED_ELEMENT:
			return selectedElement != null;
		case MenuPackageImpl.MENU__ENABLED:
			return enabled != ENABLED_EDEFAULT;
