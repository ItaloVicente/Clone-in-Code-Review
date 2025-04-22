        dirtyEditorReuseGroup.setEnabled(reuseEditors.getSelection());
        openNewEditor.setSelection(!store
                .getDefaultBoolean(IPreferenceConstants.REUSE_DIRTY_EDITORS));
        openNewEditor.setEnabled(reuseEditors.getSelection());
        promptToReuseEditor.setSelection(store
                .getDefaultBoolean(IPreferenceConstants.REUSE_DIRTY_EDITORS));
        promptToReuseEditor.setEnabled(reuseEditors.getSelection());
