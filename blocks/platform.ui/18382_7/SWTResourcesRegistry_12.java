	
	public void invalidateResources(Class<?>... clsToInvalidate) {		
		for (Class<?> cls: clsToInvalidate) {
			for (Object obj: getResourceByType(cls)) {
				if (obj instanceof VolatileResource<?>) {	
					((VolatileResource<?>) obj).setValid(false);
				}
			}
		}
	}
