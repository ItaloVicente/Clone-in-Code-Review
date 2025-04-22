		case BasicPackageImpl.PART_DESCRIPTOR__LABEL:
			setLabel(LABEL_EDEFAULT);
			return;
		case BasicPackageImpl.PART_DESCRIPTOR__ICON_URI:
			setIconURI(ICON_URI_EDEFAULT);
			return;
		case BasicPackageImpl.PART_DESCRIPTOR__TOOLTIP:
			setTooltip(TOOLTIP_EDEFAULT);
			return;
		case BasicPackageImpl.PART_DESCRIPTOR__HANDLERS:
			getHandlers().clear();
			return;
		case BasicPackageImpl.PART_DESCRIPTOR__BINDING_CONTEXTS:
			getBindingContexts().clear();
			return;
		case BasicPackageImpl.PART_DESCRIPTOR__ALLOW_MULTIPLE:
			setAllowMultiple(ALLOW_MULTIPLE_EDEFAULT);
			return;
		case BasicPackageImpl.PART_DESCRIPTOR__CATEGORY:
			setCategory(CATEGORY_EDEFAULT);
			return;
		case BasicPackageImpl.PART_DESCRIPTOR__MENUS:
			getMenus().clear();
			return;
		case BasicPackageImpl.PART_DESCRIPTOR__TOOLBAR:
			setToolbar((MToolBar)null);
			return;
		case BasicPackageImpl.PART_DESCRIPTOR__CLOSEABLE:
			setCloseable(CLOSEABLE_EDEFAULT);
			return;
		case BasicPackageImpl.PART_DESCRIPTOR__DIRTYABLE:
			setDirtyable(DIRTYABLE_EDEFAULT);
			return;
		case BasicPackageImpl.PART_DESCRIPTOR__CONTRIBUTION_URI:
			setContributionURI(CONTRIBUTION_URI_EDEFAULT);
			return;
		case BasicPackageImpl.PART_DESCRIPTOR__DESCRIPTION:
			setDescription(DESCRIPTION_EDEFAULT);
			return;
		case BasicPackageImpl.PART_DESCRIPTOR__VARIABLES:
			getVariables().clear();
			return;
		case BasicPackageImpl.PART_DESCRIPTOR__PROPERTIES:
			getProperties().clear();
			return;
		case BasicPackageImpl.PART_DESCRIPTOR__TRIM_BARS:
			getTrimBars().clear();
			return;
