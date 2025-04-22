    /**
     * Activates the given nested editor.
     *
     * @param part the nested editor
     * @since 3.0
     */
    public void activateEditor(IEditorPart part) {
        activeEditorIndex = getIndex(part);
