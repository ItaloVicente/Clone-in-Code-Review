    /**
     * Unregisters listeners.
     *
     * @since 3.1
     */
    private void unregisterListeners() {
        Platform.getExtensionRegistry().removeRegistryChangeListener(
                registryListener);
        workbenchWindow.getExtensionTracker().unregisterHandler(configListener);
    }
