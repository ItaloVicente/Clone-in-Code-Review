			if (null != name) // Check if it is a binding definition.
				switch (name) {
				case TAG_KEY_BINDING:
					addElementToIndexedArray(configurationElement, indexedConfigurationElements, INDEX_BINDING_DEFINITIONS,
							bindingDefinitionCount++);

					break;
				case TAG_KEY_CONFIGURATION:
					addElementToIndexedArray(configurationElement, indexedConfigurationElements, INDEX_SCHEME_DEFINITIONS,
							schemeDefinitionCount++);

					break;
				case TAG_ACTIVE_KEY_CONFIGURATION:
					addElementToIndexedArray(configurationElement, indexedConfigurationElements, INDEX_ACTIVE_SCHEME,
							activeSchemeElementCount++);
					break;
				default:
					break;
				}
