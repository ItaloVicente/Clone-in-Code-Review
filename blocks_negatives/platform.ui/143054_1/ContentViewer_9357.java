    /**
     * Returns the content provider used by this viewer,
     * or <code>null</code> if this view does not yet have a content
     * provider.
     * <p>
     * The <code>ContentViewer</code> implementation of this method returns the content
     * provider recorded is an internal state variable.
     * Overriding this method is generally not required;
     * however, if overriding in a subclass,
     * <code>super.getContentProvider</code> must be invoked.
     * </p>
     *
     * @return the content provider, or <code>null</code> if none
     */
    public IContentProvider getContentProvider() {
        return contentProvider;
    }
