    /**
     * Sets the label provider for this viewer.
     * <p>
     * The <code>ContentViewer</code> implementation of this method ensures that the
     * given label provider is connected to this viewer and the
     * former label provider is disconnected from this viewer.
     * Overriding this method is generally not required;
     * however, if overriding in a subclass,
     * <code>super.setLabelProvider</code> must be invoked.
     * </p>
     *
     * @param labelProvider the label provider, or <code>null</code> if none
     */
    public void setLabelProvider(IBaseLabelProvider labelProvider) {
        IBaseLabelProvider oldProvider = this.labelProvider;
        if (labelProvider == oldProvider) {
            return;
        }
        if (oldProvider != null) {
            oldProvider.removeListener(this.labelProviderListener);
        }
        this.labelProvider = labelProvider;
        if (labelProvider != null) {
            labelProvider.addListener(this.labelProviderListener);
        }
        refresh();
