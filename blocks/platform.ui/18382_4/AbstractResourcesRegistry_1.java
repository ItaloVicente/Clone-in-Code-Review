	
	protected Collection getResourceByType(Object type) {
		if (allResourcesMap != null) {
			Map resourcesMap = (Map) allResourcesMap.get(type);
			if (resourcesMap != null) {
				return resourcesMap.values();
			}
		}
		return Collections.EMPTY_LIST;
	}
