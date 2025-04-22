			case AdvancedPackageImpl.PERSPECTIVE__LABEL:
				setLabel((String)newValue);
				return;
			case AdvancedPackageImpl.PERSPECTIVE__ICON_URI:
				setIconURI((String)newValue);
				return;
			case AdvancedPackageImpl.PERSPECTIVE__TOOLTIP:
				setTooltip((String)newValue);
				return;
			case AdvancedPackageImpl.PERSPECTIVE__CONTEXT:
				setContext((IEclipseContext)newValue);
				return;
			case AdvancedPackageImpl.PERSPECTIVE__VARIABLES:
				getVariables().clear();
				getVariables().addAll((Collection<? extends String>)newValue);
				return;
			case AdvancedPackageImpl.PERSPECTIVE__PROPERTIES:
				((EStructuralFeature.Setting)((EMap.InternalMapView<String, String>)getProperties()).eMap()).set(newValue);
				return;
			case AdvancedPackageImpl.PERSPECTIVE__HANDLERS:
				getHandlers().clear();
				getHandlers().addAll((Collection<? extends MHandler>)newValue);
				return;
			case AdvancedPackageImpl.PERSPECTIVE__BINDING_CONTEXTS:
				getBindingContexts().clear();
				getBindingContexts().addAll((Collection<? extends MBindingContext>)newValue);
				return;
			case AdvancedPackageImpl.PERSPECTIVE__WINDOWS:
				getWindows().clear();
				getWindows().addAll((Collection<? extends MWindow>)newValue);
				return;
			case AdvancedPackageImpl.PERSPECTIVE__TRIM_BARS:
				getTrimBars().clear();
				getTrimBars().addAll((Collection<? extends MTrimBar>)newValue);
				return;
