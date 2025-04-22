    }

    /**
     * Refreshes the actions for the plugin.
     * This method is called from <code>startup</code>.
     * <p>
     * This framework method may be overridden, although this is typically
     * unnecessary.
     * </p>
     */
    protected void refreshPluginActions() {
        if (!PlatformUI.isWorkbenchRunning()) {
