		case AdvancedPackageImpl.PERSPECTIVE__LABEL:
			return getLabel();
		case AdvancedPackageImpl.PERSPECTIVE__ICON_URI:
			return getIconURI();
		case AdvancedPackageImpl.PERSPECTIVE__TOOLTIP:
			return getTooltip();
		case AdvancedPackageImpl.PERSPECTIVE__LOCALIZED_LABEL:
			return getLocalizedLabel();
		case AdvancedPackageImpl.PERSPECTIVE__LOCALIZED_TOOLTIP:
			return getLocalizedTooltip();
		case AdvancedPackageImpl.PERSPECTIVE__CONTEXT:
			return getContext();
		case AdvancedPackageImpl.PERSPECTIVE__VARIABLES:
			return getVariables();
		case AdvancedPackageImpl.PERSPECTIVE__PROPERTIES:
			if (coreType)
				return ((EMap.InternalMapView<String, String>) getProperties()).eMap();
			else
				return getProperties();
		case AdvancedPackageImpl.PERSPECTIVE__HANDLERS:
			return getHandlers();
		case AdvancedPackageImpl.PERSPECTIVE__BINDING_CONTEXTS:
			return getBindingContexts();
		case AdvancedPackageImpl.PERSPECTIVE__WINDOWS:
			return getWindows();
		case AdvancedPackageImpl.PERSPECTIVE__TRIM_BARS:
			return getTrimBars();
