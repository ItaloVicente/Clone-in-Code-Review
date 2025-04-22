			case BasicPackageImpl.WINDOW__LABEL:
				return LABEL_EDEFAULT == null ? label != null : !LABEL_EDEFAULT.equals(label);
			case BasicPackageImpl.WINDOW__ICON_URI:
				return ICON_URI_EDEFAULT == null ? iconURI != null : !ICON_URI_EDEFAULT.equals(iconURI);
			case BasicPackageImpl.WINDOW__TOOLTIP:
				return TOOLTIP_EDEFAULT == null ? tooltip != null : !TOOLTIP_EDEFAULT.equals(tooltip);
			case BasicPackageImpl.WINDOW__LOCALIZED_LABEL:
				return LOCALIZED_LABEL_EDEFAULT == null ? getLocalizedLabel() != null : !LOCALIZED_LABEL_EDEFAULT.equals(getLocalizedLabel());
			case BasicPackageImpl.WINDOW__LOCALIZED_TOOLTIP:
				return LOCALIZED_TOOLTIP_EDEFAULT == null ? getLocalizedTooltip() != null : !LOCALIZED_TOOLTIP_EDEFAULT.equals(getLocalizedTooltip());
			case BasicPackageImpl.WINDOW__CONTEXT:
				return CONTEXT_EDEFAULT == null ? context != null : !CONTEXT_EDEFAULT.equals(context);
			case BasicPackageImpl.WINDOW__VARIABLES:
				return variables != null && !variables.isEmpty();
			case BasicPackageImpl.WINDOW__PROPERTIES:
				return properties != null && !properties.isEmpty();
			case BasicPackageImpl.WINDOW__HANDLERS:
				return handlers != null && !handlers.isEmpty();
			case BasicPackageImpl.WINDOW__BINDING_CONTEXTS:
				return bindingContexts != null && !bindingContexts.isEmpty();
			case BasicPackageImpl.WINDOW__SNIPPETS:
				return snippets != null && !snippets.isEmpty();
			case BasicPackageImpl.WINDOW__MAIN_MENU:
				return mainMenu != null;
			case BasicPackageImpl.WINDOW__X:
				return x != X_EDEFAULT;
			case BasicPackageImpl.WINDOW__Y:
				return y != Y_EDEFAULT;
			case BasicPackageImpl.WINDOW__WIDTH:
				return width != WIDTH_EDEFAULT;
			case BasicPackageImpl.WINDOW__HEIGHT:
				return height != HEIGHT_EDEFAULT;
			case BasicPackageImpl.WINDOW__WINDOWS:
				return windows != null && !windows.isEmpty();
			case BasicPackageImpl.WINDOW__SHARED_ELEMENTS:
				return sharedElements != null && !sharedElements.isEmpty();
