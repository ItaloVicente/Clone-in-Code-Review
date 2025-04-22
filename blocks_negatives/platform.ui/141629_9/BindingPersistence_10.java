			if (TAG_KEY_BINDING.equals(name)) {
				addElementToIndexedArray(configurationElement, indexedConfigurationElements, INDEX_BINDING_DEFINITIONS,
						bindingDefinitionCount++);

			} else if (TAG_KEY_CONFIGURATION.equals(name)) {
				addElementToIndexedArray(configurationElement, indexedConfigurationElements, INDEX_SCHEME_DEFINITIONS,
						schemeDefinitionCount++);

			} else if (TAG_ACTIVE_KEY_CONFIGURATION.equals(name)) {
				addElementToIndexedArray(configurationElement, indexedConfigurationElements, INDEX_ACTIVE_SCHEME,
						activeSchemeElementCount++);
			}
