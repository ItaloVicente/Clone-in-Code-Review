		String factoryID = childMem.getString(TAG_FACTORY_ID);
		IAdaptable frameInput = null;
		if (factoryID != null) {
			IElementFactory factory = PlatformUI.getWorkbench()
					.getElementFactory(factoryID);
			if (factory != null) {
