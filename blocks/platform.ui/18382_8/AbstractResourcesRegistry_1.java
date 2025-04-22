	protected Map getCacheByType(Object type) {
		if (allResourcesMap != null) {
			Map resourcesMap = (Map) allResourcesMap.get(type);
			if (resourcesMap != null) {
				return resourcesMap;
			}
		}
		return Collections.EMPTY_MAP;
	}

