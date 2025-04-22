        getSite().getPage().removePartListener(partListener);
        super.dispose();
    }

    /**
     * An editor has been activated.  Set the selection in this navigator
     * to be the editor's input, if linking is enabled.
     */
    void editorActivated(IEditorPart editor) {
        if (!isLinkingEnabled()) {
