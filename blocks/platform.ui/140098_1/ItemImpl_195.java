		case MenuPackageImpl.ITEM__LABEL:
			return LABEL_EDEFAULT == null ? label != null : !LABEL_EDEFAULT.equals(label);
		case MenuPackageImpl.ITEM__ICON_URI:
			return ICON_URI_EDEFAULT == null ? iconURI != null : !ICON_URI_EDEFAULT.equals(iconURI);
		case MenuPackageImpl.ITEM__TOOLTIP:
			return TOOLTIP_EDEFAULT == null ? tooltip != null : !TOOLTIP_EDEFAULT.equals(tooltip);
		case MenuPackageImpl.ITEM__LOCALIZED_LABEL:
			return LOCALIZED_LABEL_EDEFAULT == null ? getLocalizedLabel() != null
					: !LOCALIZED_LABEL_EDEFAULT.equals(getLocalizedLabel());
		case MenuPackageImpl.ITEM__LOCALIZED_TOOLTIP:
			return LOCALIZED_TOOLTIP_EDEFAULT == null ? getLocalizedTooltip() != null
					: !LOCALIZED_TOOLTIP_EDEFAULT.equals(getLocalizedTooltip());
		case MenuPackageImpl.ITEM__ENABLED:
			return enabled != ENABLED_EDEFAULT;
		case MenuPackageImpl.ITEM__SELECTED:
			return selected != SELECTED_EDEFAULT;
		case MenuPackageImpl.ITEM__TYPE:
			return type != TYPE_EDEFAULT;
