		case BasicPackageImpl.PART__CONTRIBUTION_URI:
			setContributionURI(CONTRIBUTION_URI_EDEFAULT);
			return;
		case BasicPackageImpl.PART__OBJECT:
			setObject(OBJECT_EDEFAULT);
			return;
		case BasicPackageImpl.PART__CONTEXT:
			setContext(CONTEXT_EDEFAULT);
			return;
		case BasicPackageImpl.PART__VARIABLES:
			getVariables().clear();
			return;
		case BasicPackageImpl.PART__PROPERTIES:
			getProperties().clear();
			return;
		case BasicPackageImpl.PART__LABEL:
			setLabel(LABEL_EDEFAULT);
			return;
		case BasicPackageImpl.PART__ICON_URI:
			setIconURI(ICON_URI_EDEFAULT);
			return;
		case BasicPackageImpl.PART__TOOLTIP:
			setTooltip(TOOLTIP_EDEFAULT);
			return;
		case BasicPackageImpl.PART__HANDLERS:
			getHandlers().clear();
			return;
		case BasicPackageImpl.PART__DIRTY:
			setDirty(DIRTY_EDEFAULT);
			return;
		case BasicPackageImpl.PART__BINDING_CONTEXTS:
			getBindingContexts().clear();
			return;
		case BasicPackageImpl.PART__MENUS:
			getMenus().clear();
			return;
		case BasicPackageImpl.PART__TOOLBAR:
			setToolbar((MToolBar) null);
			return;
		case BasicPackageImpl.PART__CLOSEABLE:
			setCloseable(CLOSEABLE_EDEFAULT);
			return;
		case BasicPackageImpl.PART__DESCRIPTION:
			setDescription(DESCRIPTION_EDEFAULT);
			return;
		case BasicPackageImpl.PART__TRIM_BARS:
			getTrimBars().clear();
			return;
