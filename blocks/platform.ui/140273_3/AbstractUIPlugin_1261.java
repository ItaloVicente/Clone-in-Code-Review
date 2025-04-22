		return dialogSettings;
	}

	public ImageRegistry getImageRegistry() {
		if (imageRegistry == null) {
			imageRegistry = createImageRegistry();
			initializeImageRegistry(imageRegistry);
		}
		return imageRegistry;
	}

	public IPreferenceStore getPreferenceStore() {
		if (preferenceStore == null) {
