			case BasicPackageImpl.PART__CONTRIBUTION_URI:
				setContributionURI((String)newValue);
				return;
			case BasicPackageImpl.PART__OBJECT:
				setObject(newValue);
				return;
			case BasicPackageImpl.PART__CONTEXT:
				setContext((IEclipseContext)newValue);
				return;
			case BasicPackageImpl.PART__VARIABLES:
				getVariables().clear();
				getVariables().addAll((Collection<? extends String>)newValue);
				return;
			case BasicPackageImpl.PART__PROPERTIES:
				((EStructuralFeature.Setting)((EMap.InternalMapView<String, String>)getProperties()).eMap()).set(newValue);
				return;
			case BasicPackageImpl.PART__LABEL:
				setLabel((String)newValue);
				return;
			case BasicPackageImpl.PART__ICON_URI:
				setIconURI((String)newValue);
				return;
			case BasicPackageImpl.PART__TOOLTIP:
				setTooltip((String)newValue);
				return;
			case BasicPackageImpl.PART__HANDLERS:
				getHandlers().clear();
				getHandlers().addAll((Collection<? extends MHandler>)newValue);
				return;
			case BasicPackageImpl.PART__DIRTY:
				setDirty((Boolean)newValue);
				return;
			case BasicPackageImpl.PART__BINDING_CONTEXTS:
				getBindingContexts().clear();
				getBindingContexts().addAll((Collection<? extends MBindingContext>)newValue);
				return;
			case BasicPackageImpl.PART__MENUS:
				getMenus().clear();
				getMenus().addAll((Collection<? extends MMenu>)newValue);
				return;
			case BasicPackageImpl.PART__TOOLBAR:
				setToolbar((MToolBar)newValue);
				return;
			case BasicPackageImpl.PART__CLOSEABLE:
				setCloseable((Boolean)newValue);
				return;
			case BasicPackageImpl.PART__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case BasicPackageImpl.PART__TRIM_BARS:
				getTrimBars().clear();
				getTrimBars().addAll((Collection<? extends MTrimBar>)newValue);
				return;
