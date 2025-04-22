    /**
     * Returns the label provider used by this viewer.
     * <p>
     * The <code>ContentViewer</code> implementation of this method returns the label
     * provider recorded in an internal state variable; if none has been
     * set (with <code>setLabelProvider</code>) a default label provider
     * will be created, remembered, and returned.
     * Overriding this method is generally not required; 
     * however, if overriding in a subclass,
     * <code>super.getLabelProvider</code> must be invoked.
     * </p>
     *
     * @return a label provider
     */
    public IBaseLabelProvider getLabelProvider() {
        if (labelProvider == null) {
