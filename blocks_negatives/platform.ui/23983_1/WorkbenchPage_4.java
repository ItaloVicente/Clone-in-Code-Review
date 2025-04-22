            }
        });
        return ret[0];
    }

    public boolean closeAllSavedEditors() {
        IEditorReference editors[] = getEditorReferences();
        IEditorReference savedEditors[] = new IEditorReference[editors.length];
        int j = 0;
        for (int i = 0; i < editors.length; i++) {
            IEditorReference editor = editors[i];
            if (!editor.isDirty()) {
                savedEditors[j++] = editor;
            }
        }
        if (j == 0) {
