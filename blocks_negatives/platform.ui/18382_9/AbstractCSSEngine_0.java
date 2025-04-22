		Object newValue = null;
		String key = CSSResourcesHelpers.getCSSValueKey(value);
		IResourcesRegistry resourcesRegistry = getResourcesRegistry();
		if (resourcesRegistry != null) {
			if (key != null) {
				newValue = resourcesRegistry.getResource(toType, key);
			}
		}
