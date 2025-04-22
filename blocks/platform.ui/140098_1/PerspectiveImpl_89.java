		case AdvancedPackageImpl.PERSPECTIVE__LABEL:
			setLabel(LABEL_EDEFAULT);
			return;
		case AdvancedPackageImpl.PERSPECTIVE__ICON_URI:
			setIconURI(ICON_URI_EDEFAULT);
			return;
		case AdvancedPackageImpl.PERSPECTIVE__TOOLTIP:
			setTooltip(TOOLTIP_EDEFAULT);
			return;
		case AdvancedPackageImpl.PERSPECTIVE__CONTEXT:
			setContext(CONTEXT_EDEFAULT);
			return;
		case AdvancedPackageImpl.PERSPECTIVE__VARIABLES:
			getVariables().clear();
			return;
		case AdvancedPackageImpl.PERSPECTIVE__PROPERTIES:
			getProperties().clear();
			return;
		case AdvancedPackageImpl.PERSPECTIVE__HANDLERS:
			getHandlers().clear();
			return;
		case AdvancedPackageImpl.PERSPECTIVE__BINDING_CONTEXTS:
			getBindingContexts().clear();
			return;
		case AdvancedPackageImpl.PERSPECTIVE__WINDOWS:
			getWindows().clear();
			return;
		case AdvancedPackageImpl.PERSPECTIVE__TRIM_BARS:
			getTrimBars().clear();
			return;
