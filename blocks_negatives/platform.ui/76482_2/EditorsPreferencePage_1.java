        dirtyEditorReuseGroup = new Group(editorReuseIndentGroup, SWT.NONE);
        layout = new GridLayout();
        layout.marginWidth = 0;
        dirtyEditorReuseGroup.setLayout(layout);
        dirtyEditorReuseGroup.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        dirtyEditorReuseGroup.setText(WorkbenchMessages.WorkbenchPreference_reuseDirtyEditorGroupTitle);
        dirtyEditorReuseGroup.setEnabled(reuseEditors.getSelection());

        promptToReuseEditor = new Button(dirtyEditorReuseGroup, SWT.RADIO);
        promptToReuseEditor.setText(WorkbenchMessages.WorkbenchPreference_promptToReuseEditor);
        promptToReuseEditor.setSelection(store
                .getBoolean(IPreferenceConstants.REUSE_DIRTY_EDITORS));
        promptToReuseEditor.setEnabled(reuseEditors.getSelection());

        openNewEditor = new Button(dirtyEditorReuseGroup, SWT.RADIO);
        openNewEditor.setText(WorkbenchMessages.WorkbenchPreference_openNewEditor);
        openNewEditor.setSelection(!store
                .getBoolean(IPreferenceConstants.REUSE_DIRTY_EDITORS));
        openNewEditor.setEnabled(reuseEditors.getSelection());
