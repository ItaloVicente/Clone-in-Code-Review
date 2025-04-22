    /**
     * Disposes this action by removing it as a listener from the selection provider.
     * This must be called by the creator of the action when the action is no longer needed.
     */
    public void dispose() {
        provider.removeSelectionChangedListener(this);
    }
