			if (name != null) // Check if it is a binding definition.
				switch (name) {
				case TAG_COMMAND:
					addElementToIndexedArray(configurationElement, indexedConfigurationElements, INDEX_COMMAND_DEFINITIONS,
							commandDefinitionCount++);
					break;
				case TAG_CATEGORY:
					addElementToIndexedArray(configurationElement, indexedConfigurationElements, INDEX_CATEGORY_DEFINITIONS,
							categoryDefinitionCount++);
					break;
				case TAG_COMMAND_PARAMETER_TYPE:
					addElementToIndexedArray(configurationElement, indexedConfigurationElements,
							INDEX_PARAMETER_TYPE_DEFINITIONS, parameterTypeDefinitionCount++);
					break;
				default:
					break;
				}
