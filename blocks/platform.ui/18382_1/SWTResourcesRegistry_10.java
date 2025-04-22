	
	public void invalidateResources(Class<?>... clsToInvalidate) {		
		for (Class<?> cls: clsToInvalidate) {
			for (Object obj: getResourceByType(cls)) {
				if (obj instanceof VolatileResource<?>) {	
					((VolatileResource<?>) obj).setValid(false);
				}
			}
		}
	}
	
	public void addUnusedResource(Resource resource) {
		unusedResources.add(resource);
	}
	
	public void disposeUnusedResources() {
		if (!unusedResources.isEmpty()) {
			for (Resource resource: unusedResources) {
				if (resource != null && !resource.isDisposed()) {
					resource.dispose();
				}
			}
			unusedResources.clear();
		}
	}
