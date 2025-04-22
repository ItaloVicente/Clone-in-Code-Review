    /**
     * Returns the editor referenced by this object.
     * Returns <code>null</code> if the editor was not instantiated or
     * it failed to be restored. Tries to restore the editor
     * if <code>restore</code> is true.
     */
    IEditorPart getEditor(boolean restore);
