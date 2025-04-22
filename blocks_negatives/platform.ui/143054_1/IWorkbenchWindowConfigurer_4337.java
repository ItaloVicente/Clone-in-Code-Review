    /**
     * Configures the drop target listener for the editor area of this workbench window.
     * <p>
     * The workbench advisor ordinarily calls this method from the
     * <code>preWindowOpen</code> callback.
     * A newly-created workbench window has no configured drop target listener for its
     * editor area.
     * </p>
     * <p>
     * Note that drag and drop to the editor area requires adding one or more
     * transfer types (using <code>addEditorAreaTransfer</code>) and
     * configuring a drop target listener
     * (with <code>configureEditorAreaDropListener</code>)
     * capable of handling any of those transfer types.
     * </p>
     *
     * @param dropTargetListener the drop target listener that will handle
     * requests to drop an object on to the editor area of this window
     *
     * @see #addEditorAreaTransfer
     */
    void configureEditorAreaDropListener(
            DropTargetListener dropTargetListener);
