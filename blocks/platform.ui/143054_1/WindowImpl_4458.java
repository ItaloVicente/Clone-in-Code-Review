		case BasicPackageImpl.WINDOW__LABEL:
			setLabel(LABEL_EDEFAULT);
			return;
		case BasicPackageImpl.WINDOW__ICON_URI:
			setIconURI(ICON_URI_EDEFAULT);
			return;
		case BasicPackageImpl.WINDOW__TOOLTIP:
			setTooltip(TOOLTIP_EDEFAULT);
			return;
		case BasicPackageImpl.WINDOW__CONTEXT:
			setContext(CONTEXT_EDEFAULT);
			return;
		case BasicPackageImpl.WINDOW__VARIABLES:
			getVariables().clear();
			return;
		case BasicPackageImpl.WINDOW__PROPERTIES:
			getProperties().clear();
			return;
		case BasicPackageImpl.WINDOW__HANDLERS:
			getHandlers().clear();
			return;
		case BasicPackageImpl.WINDOW__BINDING_CONTEXTS:
			getBindingContexts().clear();
			return;
		case BasicPackageImpl.WINDOW__SNIPPETS:
			getSnippets().clear();
			return;
		case BasicPackageImpl.WINDOW__MAIN_MENU:
			setMainMenu((MMenu) null);
			return;
		case BasicPackageImpl.WINDOW__X:
			setX(X_EDEFAULT);
			return;
		case BasicPackageImpl.WINDOW__Y:
			setY(Y_EDEFAULT);
			return;
		case BasicPackageImpl.WINDOW__WIDTH:
			setWidth(WIDTH_EDEFAULT);
			return;
		case BasicPackageImpl.WINDOW__HEIGHT:
			setHeight(HEIGHT_EDEFAULT);
			return;
		case BasicPackageImpl.WINDOW__WINDOWS:
			getWindows().clear();
			return;
		case BasicPackageImpl.WINDOW__SHARED_ELEMENTS:
			getSharedElements().clear();
			return;
