	private void unregisterListeners() {
		Platform.getExtensionRegistry().removeRegistryChangeListener(registryListener);
		IExtensionTracker extensionTracker = workbenchWindow.getExtensionTracker();
		if (extensionTracker != null) {
			extensionTracker.unregisterHandler(configListener);
		}
	}
