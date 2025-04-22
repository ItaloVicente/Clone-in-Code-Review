				String generatorName = element.getAttribute(ATTRIBUTE_GENERATOR_ID);
				Collection<IConfigurationElement> extensionCollection;
				if(generatorExtensions.containsKey(generatorName)) {
					extensionCollection = generatorExtensions.get(generatorName);
				} else {
					extensionCollection = new ArrayList<>();
				}
