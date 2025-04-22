        this.workbench = theWorkbench;
        this.selection = currentSelection;

        initializeDefaultPageImageDescriptor();
    }

    /**
     * Initializes the default page image descriptor to an appropriate banner.
     * By calling <code>setDefaultPageImageDescriptor</code>.
     * The default implementation of this method uses a generic new wizard image.
     * <p>
     * Subclasses may reimplement.
     * </p>
     */
    protected void initializeDefaultPageImageDescriptor() {
