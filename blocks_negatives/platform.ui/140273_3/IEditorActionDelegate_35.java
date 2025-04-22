    /**
     * Sets the active editor for the delegate.
     * Implementors should disconnect from the old editor, connect to the
     * new editor, and update the action to reflect the new editor.
     *
     * @param action the action proxy that handles presentation portion of the action
     * @param targetEditor the new editor target
     */
    void setActiveEditor(IAction action, IEditorPart targetEditor);
