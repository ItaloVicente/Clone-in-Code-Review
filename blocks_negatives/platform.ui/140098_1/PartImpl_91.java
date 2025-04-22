			case BasicPackageImpl.PART__CONTRIBUTION_URI:
				return CONTRIBUTION_URI_EDEFAULT == null ? contributionURI != null : !CONTRIBUTION_URI_EDEFAULT.equals(contributionURI);
			case BasicPackageImpl.PART__OBJECT:
				return OBJECT_EDEFAULT == null ? object != null : !OBJECT_EDEFAULT.equals(object);
			case BasicPackageImpl.PART__CONTEXT:
				return CONTEXT_EDEFAULT == null ? context != null : !CONTEXT_EDEFAULT.equals(context);
			case BasicPackageImpl.PART__VARIABLES:
				return variables != null && !variables.isEmpty();
			case BasicPackageImpl.PART__PROPERTIES:
				return properties != null && !properties.isEmpty();
			case BasicPackageImpl.PART__LABEL:
				return LABEL_EDEFAULT == null ? label != null : !LABEL_EDEFAULT.equals(label);
			case BasicPackageImpl.PART__ICON_URI:
				return ICON_URI_EDEFAULT == null ? iconURI != null : !ICON_URI_EDEFAULT.equals(iconURI);
			case BasicPackageImpl.PART__TOOLTIP:
				return TOOLTIP_EDEFAULT == null ? tooltip != null : !TOOLTIP_EDEFAULT.equals(tooltip);
			case BasicPackageImpl.PART__LOCALIZED_LABEL:
				return LOCALIZED_LABEL_EDEFAULT == null ? getLocalizedLabel() != null : !LOCALIZED_LABEL_EDEFAULT.equals(getLocalizedLabel());
			case BasicPackageImpl.PART__LOCALIZED_TOOLTIP:
				return LOCALIZED_TOOLTIP_EDEFAULT == null ? getLocalizedTooltip() != null : !LOCALIZED_TOOLTIP_EDEFAULT.equals(getLocalizedTooltip());
			case BasicPackageImpl.PART__HANDLERS:
				return handlers != null && !handlers.isEmpty();
			case BasicPackageImpl.PART__DIRTY:
				return dirty != DIRTY_EDEFAULT;
			case BasicPackageImpl.PART__BINDING_CONTEXTS:
				return bindingContexts != null && !bindingContexts.isEmpty();
			case BasicPackageImpl.PART__MENUS:
				return menus != null && !menus.isEmpty();
			case BasicPackageImpl.PART__TOOLBAR:
				return toolbar != null;
			case BasicPackageImpl.PART__CLOSEABLE:
				return closeable != CLOSEABLE_EDEFAULT;
			case BasicPackageImpl.PART__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case BasicPackageImpl.PART__LOCALIZED_DESCRIPTION:
				return LOCALIZED_DESCRIPTION_EDEFAULT == null ? getLocalizedDescription() != null : !LOCALIZED_DESCRIPTION_EDEFAULT.equals(getLocalizedDescription());
			case BasicPackageImpl.PART__TRIM_BARS:
				return trimBars != null && !trimBars.isEmpty();
