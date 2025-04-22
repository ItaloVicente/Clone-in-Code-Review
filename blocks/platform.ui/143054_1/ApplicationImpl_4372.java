		case ApplicationPackageImpl.APPLICATION__CONTEXT:
			setContext(CONTEXT_EDEFAULT);
			return;
		case ApplicationPackageImpl.APPLICATION__VARIABLES:
			getVariables().clear();
			return;
		case ApplicationPackageImpl.APPLICATION__PROPERTIES:
			getProperties().clear();
			return;
		case ApplicationPackageImpl.APPLICATION__HANDLERS:
			getHandlers().clear();
			return;
		case ApplicationPackageImpl.APPLICATION__BINDING_TABLES:
			getBindingTables().clear();
			return;
		case ApplicationPackageImpl.APPLICATION__ROOT_CONTEXT:
			getRootContext().clear();
			return;
		case ApplicationPackageImpl.APPLICATION__DESCRIPTORS:
			getDescriptors().clear();
			return;
		case ApplicationPackageImpl.APPLICATION__BINDING_CONTEXTS:
			getBindingContexts().clear();
			return;
		case ApplicationPackageImpl.APPLICATION__MENU_CONTRIBUTIONS:
			getMenuContributions().clear();
			return;
		case ApplicationPackageImpl.APPLICATION__TOOL_BAR_CONTRIBUTIONS:
			getToolBarContributions().clear();
			return;
		case ApplicationPackageImpl.APPLICATION__TRIM_CONTRIBUTIONS:
			getTrimContributions().clear();
			return;
		case ApplicationPackageImpl.APPLICATION__SNIPPETS:
			getSnippets().clear();
			return;
		case ApplicationPackageImpl.APPLICATION__COMMANDS:
			getCommands().clear();
			return;
		case ApplicationPackageImpl.APPLICATION__ADDONS:
			getAddons().clear();
			return;
		case ApplicationPackageImpl.APPLICATION__CATEGORIES:
			getCategories().clear();
			return;
		case ApplicationPackageImpl.APPLICATION__DIALOGS:
			getDialogs().clear();
			return;
