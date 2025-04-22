		case AdvancedPackageImpl.PERSPECTIVE__LABEL:
			return LABEL_EDEFAULT == null ? label != null : !LABEL_EDEFAULT.equals(label);
		case AdvancedPackageImpl.PERSPECTIVE__ICON_URI:
			return ICON_URI_EDEFAULT == null ? iconURI != null : !ICON_URI_EDEFAULT.equals(iconURI);
		case AdvancedPackageImpl.PERSPECTIVE__TOOLTIP:
			return TOOLTIP_EDEFAULT == null ? tooltip != null : !TOOLTIP_EDEFAULT.equals(tooltip);
		case AdvancedPackageImpl.PERSPECTIVE__LOCALIZED_LABEL:
			return LOCALIZED_LABEL_EDEFAULT == null ? getLocalizedLabel() != null
					: !LOCALIZED_LABEL_EDEFAULT.equals(getLocalizedLabel());
		case AdvancedPackageImpl.PERSPECTIVE__LOCALIZED_TOOLTIP:
			return LOCALIZED_TOOLTIP_EDEFAULT == null ? getLocalizedTooltip() != null
					: !LOCALIZED_TOOLTIP_EDEFAULT.equals(getLocalizedTooltip());
		case AdvancedPackageImpl.PERSPECTIVE__CONTEXT:
			return CONTEXT_EDEFAULT == null ? context != null : !CONTEXT_EDEFAULT.equals(context);
		case AdvancedPackageImpl.PERSPECTIVE__VARIABLES:
			return variables != null && !variables.isEmpty();
		case AdvancedPackageImpl.PERSPECTIVE__PROPERTIES:
			return properties != null && !properties.isEmpty();
		case AdvancedPackageImpl.PERSPECTIVE__HANDLERS:
			return handlers != null && !handlers.isEmpty();
		case AdvancedPackageImpl.PERSPECTIVE__BINDING_CONTEXTS:
			return bindingContexts != null && !bindingContexts.isEmpty();
		case AdvancedPackageImpl.PERSPECTIVE__WINDOWS:
			return windows != null && !windows.isEmpty();
		case AdvancedPackageImpl.PERSPECTIVE__TRIM_BARS:
			return trimBars != null && !trimBars.isEmpty();
