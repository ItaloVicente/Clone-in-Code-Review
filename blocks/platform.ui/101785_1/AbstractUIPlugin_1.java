		if (imageManager == null) {
			imageManager = new UiPluginImageManager();
		}
		ImageRegistry imageRegistry = imageManager.getImageRegistry();
		if (imageMangerInitialized == false) {
			initializeImageRegistry(imageRegistry);
			imageMangerInitialized = true;
		}
		return imageRegistry;
