		case MenuPackageImpl.POPUP_MENU__CONTEXT:
			return getContext();
		case MenuPackageImpl.POPUP_MENU__VARIABLES:
			return getVariables();
		case MenuPackageImpl.POPUP_MENU__PROPERTIES:
			if (coreType)
				return ((EMap.InternalMapView<String, String>) getProperties()).eMap();
			else
				return getProperties();
