			case AdvancedPackageImpl.AREA__LABEL:
				return LABEL_EDEFAULT == null ? label != null : !LABEL_EDEFAULT.equals(label);
			case AdvancedPackageImpl.AREA__ICON_URI:
				return ICON_URI_EDEFAULT == null ? iconURI != null : !ICON_URI_EDEFAULT.equals(iconURI);
			case AdvancedPackageImpl.AREA__TOOLTIP:
				return TOOLTIP_EDEFAULT == null ? tooltip != null : !TOOLTIP_EDEFAULT.equals(tooltip);
			case AdvancedPackageImpl.AREA__LOCALIZED_LABEL:
				return LOCALIZED_LABEL_EDEFAULT == null ? getLocalizedLabel() != null : !LOCALIZED_LABEL_EDEFAULT.equals(getLocalizedLabel());
			case AdvancedPackageImpl.AREA__LOCALIZED_TOOLTIP:
				return LOCALIZED_TOOLTIP_EDEFAULT == null ? getLocalizedTooltip() != null : !LOCALIZED_TOOLTIP_EDEFAULT.equals(getLocalizedTooltip());
