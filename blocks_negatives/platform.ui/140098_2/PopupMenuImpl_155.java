			case MenuPackageImpl.POPUP_MENU__CONTEXT:
				setContext((IEclipseContext)newValue);
				return;
			case MenuPackageImpl.POPUP_MENU__VARIABLES:
				getVariables().clear();
				getVariables().addAll((Collection<? extends String>)newValue);
				return;
			case MenuPackageImpl.POPUP_MENU__PROPERTIES:
				((EStructuralFeature.Setting)((EMap.InternalMapView<String, String>)getProperties()).eMap()).set(newValue);
				return;
