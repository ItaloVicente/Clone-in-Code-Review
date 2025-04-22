    /**
     * Sets the active editor for the contributor.
     * Implementors should disconnect from the old editor, connect to the
     * new editor, and update the actions to reflect the new editor.
     *
     * @param targetEditor the new editor target
     */
    void setActiveEditor(IEditorPart targetEditor);
