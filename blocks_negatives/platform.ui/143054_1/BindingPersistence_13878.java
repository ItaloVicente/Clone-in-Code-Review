		readSchemesFromRegistry(
				indexedConfigurationElements[INDEX_SCHEME_DEFINITIONS],
				schemeDefinitionCount, bindingManager);
		readActiveScheme(indexedConfigurationElements[INDEX_ACTIVE_SCHEME],
				activeSchemeElementCount, preferenceMemento, bindingManager);
		readBindingsFromRegistry(
				indexedConfigurationElements[INDEX_BINDING_DEFINITIONS],
				bindingDefinitionCount, bindingManager, commandManager);
		readBindingsFromPreferences(preferenceMemento, bindingManager,
				commandManager);
