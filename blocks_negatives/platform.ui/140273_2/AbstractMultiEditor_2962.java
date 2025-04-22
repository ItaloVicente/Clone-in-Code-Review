        innerEditors[activeEditorIndex].setFocus();
    }

    /**
     * Returns the active inner editor.
     * @return the active editor
     */
    public final IEditorPart getActiveEditor() {
        return innerEditors[activeEditorIndex];
    }

    /**
     * Returns an array with all inner editors.
     * @return the inner editors
     */
    public final IEditorPart[] getInnerEditors() {
        return innerEditors;
    }
