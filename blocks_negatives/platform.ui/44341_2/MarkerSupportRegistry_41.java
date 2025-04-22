
				String generatorName = element
						.getAttribute(ATTRIBUTE_GENERATOR_ID);

				Collection extensionCollection;
				if(generatorExtensions.containsKey(generatorName))
					extensionCollection = (Collection) generatorExtensions.get(generatorName);
				else
					extensionCollection = new ArrayList();
