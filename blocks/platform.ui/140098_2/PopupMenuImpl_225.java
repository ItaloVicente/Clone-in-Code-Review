		case MenuPackageImpl.POPUP_MENU__CONTEXT:
			return CONTEXT_EDEFAULT == null ? context != null : !CONTEXT_EDEFAULT.equals(context);
		case MenuPackageImpl.POPUP_MENU__VARIABLES:
			return variables != null && !variables.isEmpty();
		case MenuPackageImpl.POPUP_MENU__PROPERTIES:
			return properties != null && !properties.isEmpty();
