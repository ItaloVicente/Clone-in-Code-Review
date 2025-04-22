			case ApplicationPackageImpl.APPLICATION__CONTEXT:
				return CONTEXT_EDEFAULT == null ? context != null : !CONTEXT_EDEFAULT.equals(context);
			case ApplicationPackageImpl.APPLICATION__VARIABLES:
				return variables != null && !variables.isEmpty();
			case ApplicationPackageImpl.APPLICATION__PROPERTIES:
				return properties != null && !properties.isEmpty();
			case ApplicationPackageImpl.APPLICATION__HANDLERS:
				return handlers != null && !handlers.isEmpty();
			case ApplicationPackageImpl.APPLICATION__BINDING_TABLES:
				return bindingTables != null && !bindingTables.isEmpty();
			case ApplicationPackageImpl.APPLICATION__ROOT_CONTEXT:
				return rootContext != null && !rootContext.isEmpty();
			case ApplicationPackageImpl.APPLICATION__DESCRIPTORS:
				return descriptors != null && !descriptors.isEmpty();
			case ApplicationPackageImpl.APPLICATION__BINDING_CONTEXTS:
				return bindingContexts != null && !bindingContexts.isEmpty();
			case ApplicationPackageImpl.APPLICATION__MENU_CONTRIBUTIONS:
				return menuContributions != null && !menuContributions.isEmpty();
			case ApplicationPackageImpl.APPLICATION__TOOL_BAR_CONTRIBUTIONS:
				return toolBarContributions != null && !toolBarContributions.isEmpty();
			case ApplicationPackageImpl.APPLICATION__TRIM_CONTRIBUTIONS:
				return trimContributions != null && !trimContributions.isEmpty();
			case ApplicationPackageImpl.APPLICATION__SNIPPETS:
				return snippets != null && !snippets.isEmpty();
			case ApplicationPackageImpl.APPLICATION__COMMANDS:
				return commands != null && !commands.isEmpty();
			case ApplicationPackageImpl.APPLICATION__ADDONS:
				return addons != null && !addons.isEmpty();
			case ApplicationPackageImpl.APPLICATION__CATEGORIES:
				return categories != null && !categories.isEmpty();
			case ApplicationPackageImpl.APPLICATION__DIALOGS:
				return dialogs != null && !dialogs.isEmpty();
