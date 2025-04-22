    /**
     * Handles a resource changed event by updating the enablement
     * if one of the selected projects is opened or closed.
     */
    protected void handleResourceChanged(IResourceChangeEvent event) {
        ActionContext context = getContext();
        if (context == null) {
            return;
        }
