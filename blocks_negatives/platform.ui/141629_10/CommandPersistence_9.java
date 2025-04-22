			if (TAG_COMMAND.equals(name)) {
				addElementToIndexedArray(configurationElement, indexedConfigurationElements, INDEX_COMMAND_DEFINITIONS,
						commandDefinitionCount++);
			} else if (TAG_CATEGORY.equals(name)) {
				addElementToIndexedArray(configurationElement, indexedConfigurationElements, INDEX_CATEGORY_DEFINITIONS,
						categoryDefinitionCount++);
			} else if (TAG_COMMAND_PARAMETER_TYPE.equals(name)) {
				addElementToIndexedArray(configurationElement, indexedConfigurationElements,
						INDEX_PARAMETER_TYPE_DEFINITIONS, parameterTypeDefinitionCount++);
			}
