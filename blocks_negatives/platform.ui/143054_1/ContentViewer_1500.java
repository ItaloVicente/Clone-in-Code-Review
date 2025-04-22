    /**
     * Handles a label provider changed event.
     * <p>
     * The <code>ContentViewer</code> implementation of this method calls <code>labelProviderChanged()</code>
     * to cause a complete refresh of the viewer.
     * Subclasses may reimplement or extend.
     * </p>
     * @param event the change event
     */
    protected void handleLabelProviderChanged(LabelProviderChangedEvent event) {
        labelProviderChanged();
    }
