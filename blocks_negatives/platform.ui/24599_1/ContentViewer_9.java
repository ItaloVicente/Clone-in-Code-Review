    /**
     * Notifies that the label provider has changed.
     * <p>
     * The <code>ContentViewer</code> implementation of this method calls <code>refresh()</code>.
     * Subclasses may reimplement or extend.
     * </p>
     */
    protected void labelProviderChanged() {
        refresh();
    }
