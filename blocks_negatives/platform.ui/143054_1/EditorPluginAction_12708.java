    /**
     * Handles editor change by re-registering for selection
     * changes and updating IEditorActionDelegate.
     */
    public void editorChanged(IEditorPart part) {
        if (currentEditor != null) {
