		case BasicPackageImpl.PART_DESCRIPTOR__LABEL:
			setLabel((String) newValue);
			return;
		case BasicPackageImpl.PART_DESCRIPTOR__ICON_URI:
			setIconURI((String) newValue);
			return;
		case BasicPackageImpl.PART_DESCRIPTOR__TOOLTIP:
			setTooltip((String) newValue);
			return;
		case BasicPackageImpl.PART_DESCRIPTOR__HANDLERS:
			getHandlers().clear();
			getHandlers().addAll((Collection<? extends MHandler>) newValue);
			return;
		case BasicPackageImpl.PART_DESCRIPTOR__BINDING_CONTEXTS:
			getBindingContexts().clear();
			getBindingContexts().addAll((Collection<? extends MBindingContext>) newValue);
			return;
		case BasicPackageImpl.PART_DESCRIPTOR__ALLOW_MULTIPLE:
			setAllowMultiple((Boolean) newValue);
			return;
		case BasicPackageImpl.PART_DESCRIPTOR__CATEGORY:
			setCategory((String) newValue);
			return;
		case BasicPackageImpl.PART_DESCRIPTOR__MENUS:
			getMenus().clear();
			getMenus().addAll((Collection<? extends MMenu>) newValue);
			return;
		case BasicPackageImpl.PART_DESCRIPTOR__TOOLBAR:
			setToolbar((MToolBar) newValue);
			return;
		case BasicPackageImpl.PART_DESCRIPTOR__CLOSEABLE:
			setCloseable((Boolean) newValue);
			return;
		case BasicPackageImpl.PART_DESCRIPTOR__DIRTYABLE:
			setDirtyable((Boolean) newValue);
			return;
		case BasicPackageImpl.PART_DESCRIPTOR__CONTRIBUTION_URI:
			setContributionURI((String) newValue);
			return;
		case BasicPackageImpl.PART_DESCRIPTOR__DESCRIPTION:
			setDescription((String) newValue);
			return;
		case BasicPackageImpl.PART_DESCRIPTOR__VARIABLES:
			getVariables().clear();
			getVariables().addAll((Collection<? extends String>) newValue);
			return;
		case BasicPackageImpl.PART_DESCRIPTOR__PROPERTIES:
			((EStructuralFeature.Setting) ((EMap.InternalMapView<String, String>) getProperties()).eMap())
					.set(newValue);
			return;
		case BasicPackageImpl.PART_DESCRIPTOR__TRIM_BARS:
			getTrimBars().clear();
			getTrimBars().addAll((Collection<? extends MTrimBar>) newValue);
			return;
