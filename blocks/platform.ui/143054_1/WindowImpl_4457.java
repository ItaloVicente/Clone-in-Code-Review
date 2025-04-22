		case BasicPackageImpl.WINDOW__LABEL:
			setLabel((String) newValue);
			return;
		case BasicPackageImpl.WINDOW__ICON_URI:
			setIconURI((String) newValue);
			return;
		case BasicPackageImpl.WINDOW__TOOLTIP:
			setTooltip((String) newValue);
			return;
		case BasicPackageImpl.WINDOW__CONTEXT:
			setContext((IEclipseContext) newValue);
			return;
		case BasicPackageImpl.WINDOW__VARIABLES:
			getVariables().clear();
			getVariables().addAll((Collection<? extends String>) newValue);
			return;
		case BasicPackageImpl.WINDOW__PROPERTIES:
			((EStructuralFeature.Setting) ((EMap.InternalMapView<String, String>) getProperties()).eMap())
					.set(newValue);
			return;
		case BasicPackageImpl.WINDOW__HANDLERS:
			getHandlers().clear();
			getHandlers().addAll((Collection<? extends MHandler>) newValue);
			return;
		case BasicPackageImpl.WINDOW__BINDING_CONTEXTS:
			getBindingContexts().clear();
			getBindingContexts().addAll((Collection<? extends MBindingContext>) newValue);
			return;
		case BasicPackageImpl.WINDOW__SNIPPETS:
			getSnippets().clear();
			getSnippets().addAll((Collection<? extends MUIElement>) newValue);
			return;
		case BasicPackageImpl.WINDOW__MAIN_MENU:
			setMainMenu((MMenu) newValue);
			return;
		case BasicPackageImpl.WINDOW__X:
			setX((Integer) newValue);
			return;
		case BasicPackageImpl.WINDOW__Y:
			setY((Integer) newValue);
			return;
		case BasicPackageImpl.WINDOW__WIDTH:
			setWidth((Integer) newValue);
			return;
		case BasicPackageImpl.WINDOW__HEIGHT:
			setHeight((Integer) newValue);
			return;
		case BasicPackageImpl.WINDOW__WINDOWS:
			getWindows().clear();
			getWindows().addAll((Collection<? extends MWindow>) newValue);
			return;
		case BasicPackageImpl.WINDOW__SHARED_ELEMENTS:
			getSharedElements().clear();
			getSharedElements().addAll((Collection<? extends MUIElement>) newValue);
			return;
