	
	private Object getResource(Object toType, Object key) {
		if (key != null && getResourcesRegistry() != null) {
			return getResourcesRegistry().getResource(toType, key);
		}
		return null;
	}
	
	private void registerResource(Object toType, Object key, Object resource) {
		if (key != null && resource != null && getResourcesRegistry() != null) {
			getResourcesRegistry().registerResource(toType, key, resource);
		}
	}
