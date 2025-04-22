    /**
     * Handles a dispose event on this viewer's control.
     * <p>
     * The <code>ContentViewer</code> implementation of this method disposes of this
     * viewer's label provider and content provider (if it has one).
     * Subclasses should override this method to perform any additional
     * cleanup of resources; however, overriding methods must invoke
     * <code>super.handleDispose</code>.
     * </p>
     *
     * @param event a dispose event
     */
    protected void handleDispose(DisposeEvent event) {
        if (contentProvider != null) {
            contentProvider.inputChanged(this, getInput(), null);
            contentProvider.dispose();
            contentProvider = null;
        }
        if (labelProvider != null) {
            labelProvider.removeListener(labelProviderListener);
            labelProvider.dispose();
            labelProvider = null;
        }
        input = null;
    }
