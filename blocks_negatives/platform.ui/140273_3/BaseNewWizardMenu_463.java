    /**
     * Registers listeners.
     *
     * @since 3.1
     */
    private void registerListeners() {
        Platform.getExtensionRegistry().addRegistryChangeListener(
                registryListener);
        workbenchWindow.getExtensionTracker().registerHandler(
				configListener,  null);
    }
