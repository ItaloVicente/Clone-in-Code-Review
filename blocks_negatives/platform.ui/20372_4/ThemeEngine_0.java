	@SuppressWarnings("restriction")
	private void removeSWTResourceFromCache(IResourcesRegistry registry) {
		if (registry instanceof SWTResourcesRegistry) {
			((SWTResourcesRegistry) registry)
			.removeResourcesByKeyTypeAndType(Object.class, Font.class,
					Color.class, Image.class, Cursor.class);
		}
	}

