		case MenuPackageImpl.MENU_ELEMENT__LABEL:
			return LABEL_EDEFAULT == null ? label != null : !LABEL_EDEFAULT.equals(label);
		case MenuPackageImpl.MENU_ELEMENT__ICON_URI:
			return ICON_URI_EDEFAULT == null ? iconURI != null : !ICON_URI_EDEFAULT.equals(iconURI);
		case MenuPackageImpl.MENU_ELEMENT__TOOLTIP:
			return TOOLTIP_EDEFAULT == null ? tooltip != null : !TOOLTIP_EDEFAULT.equals(tooltip);
		case MenuPackageImpl.MENU_ELEMENT__LOCALIZED_LABEL:
			return LOCALIZED_LABEL_EDEFAULT == null ? getLocalizedLabel() != null
					: !LOCALIZED_LABEL_EDEFAULT.equals(getLocalizedLabel());
		case MenuPackageImpl.MENU_ELEMENT__LOCALIZED_TOOLTIP:
			return LOCALIZED_TOOLTIP_EDEFAULT == null ? getLocalizedTooltip() != null
					: !LOCALIZED_TOOLTIP_EDEFAULT.equals(getLocalizedTooltip());
		case MenuPackageImpl.MENU_ELEMENT__MNEMONICS:
			return MNEMONICS_EDEFAULT == null ? mnemonics != null : !MNEMONICS_EDEFAULT.equals(mnemonics);
