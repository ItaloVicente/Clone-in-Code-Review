		IResourcesRegistry resourcesRegistry = getResourcesRegistry();
		if (resourcesRegistry != null) {
			if (key != null) {
				newValue = resourcesRegistry.getResource(toType, key);
			}
		}
