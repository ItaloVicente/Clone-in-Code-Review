    /**
     * Returns the extension editor action bar contributor for this editor.
     */
    public IEditorActionBarContributor getExtensionActionBarContributor() {
        EditorActionBars bars = (EditorActionBars) getActionBars();
        if (bars != null) {
