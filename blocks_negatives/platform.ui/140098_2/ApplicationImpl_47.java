			case ApplicationPackageImpl.APPLICATION__CONTEXT:
				setContext((IEclipseContext)newValue);
				return;
			case ApplicationPackageImpl.APPLICATION__VARIABLES:
				getVariables().clear();
				getVariables().addAll((Collection<? extends String>)newValue);
				return;
			case ApplicationPackageImpl.APPLICATION__PROPERTIES:
				((EStructuralFeature.Setting)((EMap.InternalMapView<String, String>)getProperties()).eMap()).set(newValue);
				return;
			case ApplicationPackageImpl.APPLICATION__HANDLERS:
				getHandlers().clear();
				getHandlers().addAll((Collection<? extends MHandler>)newValue);
				return;
			case ApplicationPackageImpl.APPLICATION__BINDING_TABLES:
				getBindingTables().clear();
				getBindingTables().addAll((Collection<? extends MBindingTable>)newValue);
				return;
			case ApplicationPackageImpl.APPLICATION__ROOT_CONTEXT:
				getRootContext().clear();
				getRootContext().addAll((Collection<? extends MBindingContext>)newValue);
				return;
			case ApplicationPackageImpl.APPLICATION__DESCRIPTORS:
				getDescriptors().clear();
				getDescriptors().addAll((Collection<? extends MPartDescriptor>)newValue);
				return;
			case ApplicationPackageImpl.APPLICATION__BINDING_CONTEXTS:
				getBindingContexts().clear();
				getBindingContexts().addAll((Collection<? extends MBindingContext>)newValue);
				return;
			case ApplicationPackageImpl.APPLICATION__MENU_CONTRIBUTIONS:
				getMenuContributions().clear();
				getMenuContributions().addAll((Collection<? extends MMenuContribution>)newValue);
				return;
			case ApplicationPackageImpl.APPLICATION__TOOL_BAR_CONTRIBUTIONS:
				getToolBarContributions().clear();
				getToolBarContributions().addAll((Collection<? extends MToolBarContribution>)newValue);
				return;
			case ApplicationPackageImpl.APPLICATION__TRIM_CONTRIBUTIONS:
				getTrimContributions().clear();
				getTrimContributions().addAll((Collection<? extends MTrimContribution>)newValue);
				return;
			case ApplicationPackageImpl.APPLICATION__SNIPPETS:
				getSnippets().clear();
				getSnippets().addAll((Collection<? extends MUIElement>)newValue);
				return;
			case ApplicationPackageImpl.APPLICATION__COMMANDS:
				getCommands().clear();
				getCommands().addAll((Collection<? extends MCommand>)newValue);
				return;
			case ApplicationPackageImpl.APPLICATION__ADDONS:
				getAddons().clear();
				getAddons().addAll((Collection<? extends MAddon>)newValue);
				return;
			case ApplicationPackageImpl.APPLICATION__CATEGORIES:
				getCategories().clear();
				getCategories().addAll((Collection<? extends MCategory>)newValue);
				return;
			case ApplicationPackageImpl.APPLICATION__DIALOGS:
				getDialogs().clear();
				getDialogs().addAll((Collection<? extends MDialog>)newValue);
				return;
