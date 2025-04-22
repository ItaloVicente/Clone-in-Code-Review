		case BasicPackageImpl.PART_DESCRIPTOR__LABEL:
			return LABEL_EDEFAULT == null ? label != null : !LABEL_EDEFAULT.equals(label);
		case BasicPackageImpl.PART_DESCRIPTOR__ICON_URI:
			return ICON_URI_EDEFAULT == null ? iconURI != null : !ICON_URI_EDEFAULT.equals(iconURI);
		case BasicPackageImpl.PART_DESCRIPTOR__TOOLTIP:
			return TOOLTIP_EDEFAULT == null ? tooltip != null : !TOOLTIP_EDEFAULT.equals(tooltip);
		case BasicPackageImpl.PART_DESCRIPTOR__LOCALIZED_LABEL:
			return LOCALIZED_LABEL_EDEFAULT == null ? getLocalizedLabel() != null
					: !LOCALIZED_LABEL_EDEFAULT.equals(getLocalizedLabel());
		case BasicPackageImpl.PART_DESCRIPTOR__LOCALIZED_TOOLTIP:
			return LOCALIZED_TOOLTIP_EDEFAULT == null ? getLocalizedTooltip() != null
					: !LOCALIZED_TOOLTIP_EDEFAULT.equals(getLocalizedTooltip());
		case BasicPackageImpl.PART_DESCRIPTOR__HANDLERS:
			return handlers != null && !handlers.isEmpty();
		case BasicPackageImpl.PART_DESCRIPTOR__BINDING_CONTEXTS:
			return bindingContexts != null && !bindingContexts.isEmpty();
		case BasicPackageImpl.PART_DESCRIPTOR__ALLOW_MULTIPLE:
			return allowMultiple != ALLOW_MULTIPLE_EDEFAULT;
		case BasicPackageImpl.PART_DESCRIPTOR__CATEGORY:
			return CATEGORY_EDEFAULT == null ? category != null : !CATEGORY_EDEFAULT.equals(category);
		case BasicPackageImpl.PART_DESCRIPTOR__MENUS:
			return menus != null && !menus.isEmpty();
		case BasicPackageImpl.PART_DESCRIPTOR__TOOLBAR:
			return toolbar != null;
		case BasicPackageImpl.PART_DESCRIPTOR__CLOSEABLE:
			return closeable != CLOSEABLE_EDEFAULT;
		case BasicPackageImpl.PART_DESCRIPTOR__DIRTYABLE:
			return dirtyable != DIRTYABLE_EDEFAULT;
		case BasicPackageImpl.PART_DESCRIPTOR__CONTRIBUTION_URI:
			return CONTRIBUTION_URI_EDEFAULT == null ? contributionURI != null
					: !CONTRIBUTION_URI_EDEFAULT.equals(contributionURI);
		case BasicPackageImpl.PART_DESCRIPTOR__DESCRIPTION:
			return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
		case BasicPackageImpl.PART_DESCRIPTOR__LOCALIZED_DESCRIPTION:
			return LOCALIZED_DESCRIPTION_EDEFAULT == null ? getLocalizedDescription() != null
					: !LOCALIZED_DESCRIPTION_EDEFAULT.equals(getLocalizedDescription());
		case BasicPackageImpl.PART_DESCRIPTOR__VARIABLES:
			return variables != null && !variables.isEmpty();
		case BasicPackageImpl.PART_DESCRIPTOR__PROPERTIES:
			return properties != null && !properties.isEmpty();
		case BasicPackageImpl.PART_DESCRIPTOR__TRIM_BARS:
			return trimBars != null && !trimBars.isEmpty();
