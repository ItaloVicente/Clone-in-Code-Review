    /**
     * Adds the given drag and drop <code>Transfer</code> type to the ones
     * supported for drag and drop on the editor area of this workbench window.
     * <p>
     * The workbench advisor would ordinarily call this method from the
     * <code>preWindowOpen</code> callback.
     * A newly-created workbench window supports no drag and drop transfer
     * types. Adding <code>EditorInputTransfer.getInstance()</code>
     * enables <code>IEditorInput</code>s to be transferred.
     * </p>
     * <p>
     * Note that drag and drop to the editor area requires adding one or more
     * transfer types (using <code>addEditorAreaTransfer</code>) and
     * configuring a drop target listener
     * (with <code>configureEditorAreaDropListener</code>)
     * capable of handling any of those transfer types.
     * </p>
     *
     * @param transfer a drag and drop transfer object
     * @see #configureEditorAreaDropListener
     * @see org.eclipse.ui.part.EditorInputTransfer
     */
    void addEditorAreaTransfer(Transfer transfer);
