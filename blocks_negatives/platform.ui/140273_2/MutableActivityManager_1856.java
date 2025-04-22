    /**
     * Unhook this manager from its registry.
     *
     * @since 3.1
     */
    public void unhookRegistryListeners() {
        activityRegistry.removeActivityRegistryListener(activityRegistryListener);
    }
