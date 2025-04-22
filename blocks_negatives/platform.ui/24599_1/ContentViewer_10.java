    /**
     * Sets the content provider used by this viewer.
     * <p>
     * The <code>ContentViewer</code> implementation of this method records the 
     * content provider in an internal state variable.
     * Overriding this method is generally not required; 
     * however, if overriding in a subclass,
     * <code>super.setContentProvider</code> must be invoked.
     * </p>
     *
     * @param contentProvider the content provider
     * @see #getContentProvider
     */
    public void setContentProvider(IContentProvider contentProvider) {
        Assert.isNotNull(contentProvider);
        IContentProvider oldContentProvider = this.contentProvider;
        this.contentProvider = contentProvider;
        if (oldContentProvider != null) {
            Object currentInput = getInput();
            oldContentProvider.inputChanged(this, currentInput, null);
            oldContentProvider.dispose();
            contentProvider.inputChanged(this, null, currentInput);
            refresh();
        }
    }
