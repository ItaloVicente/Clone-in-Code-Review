    }

    /**
     * Returns the index of the given nested editor.
     *
     * @return the index of the nested editor
     * @since 3.0
     */
    protected int getIndex(IEditorPart editor) {
        for (int i = 0; i < innerEditors.length; i++) {
            if (innerEditors[i] == editor) {
