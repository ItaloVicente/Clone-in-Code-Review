        editorReuseThresholdGroup.setLayout(layout);
        editorReuseThresholdGroup.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        reuseEditorsThreshold = new IntegerFieldEditor(
                IPreferenceConstants.REUSE_EDITORS,
                WorkbenchMessages.WorkbenchPreference_reuseEditorsThreshold, editorReuseThresholdGroup);

        reuseEditorsThreshold.setPreferenceStore(WorkbenchPlugin.getDefault()
                .getPreferenceStore());
        reuseEditorsThreshold.setPage(this);
        reuseEditorsThreshold.setTextLimit(2);
        reuseEditorsThreshold.setErrorMessage(WorkbenchMessages.WorkbenchPreference_reuseEditorsThresholdError);
        reuseEditorsThreshold
                .setValidateStrategy(StringFieldEditor.VALIDATE_ON_KEY_STROKE);
        reuseEditorsThreshold.setValidRange(1, 99);
        reuseEditorsThreshold.load();
        reuseEditorsThreshold.getLabelControl(editorReuseThresholdGroup)
                .setEnabled(reuseEditors.getSelection());
        reuseEditorsThreshold.getTextControl(editorReuseThresholdGroup)
                .setEnabled(reuseEditors.getSelection());
        reuseEditorsThreshold.setPropertyChangeListener(validityChangeListener);

    }

    /**
     * Create a composite that contains entry fields specifying editor history preferences.
     */
    protected void createEditorHistoryGroup(Composite composite) {
        Composite groupComposite = new Composite(composite, SWT.LEFT);
        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        groupComposite.setLayout(layout);
        GridData gd = new GridData();
        gd.horizontalAlignment = GridData.FILL;
        gd.grabExcessHorizontalSpace = true;
        groupComposite.setLayoutData(gd);

        recentFilesEditor = new IntegerFieldEditor(
                IPreferenceConstants.RECENT_FILES,
                WorkbenchMessages.WorkbenchPreference_recentFiles, groupComposite);

        recentFilesEditor.setPreferenceStore(WorkbenchPlugin.getDefault()
                .getPreferenceStore());
        recentFilesEditor.setPage(this);
        recentFilesEditor.setTextLimit(Integer.toString(EditorHistory.MAX_SIZE)
                .length());
        recentFilesEditor
				.setErrorMessage(NLS.bind(WorkbenchMessages.WorkbenchPreference_recentFilesError,
						Integer.valueOf(EditorHistory.MAX_SIZE)));
        recentFilesEditor
                .setValidateStrategy(StringFieldEditor.VALIDATE_ON_KEY_STROKE);
        recentFilesEditor.setValidRange(0, EditorHistory.MAX_SIZE);
        recentFilesEditor.load();
        recentFilesEditor.setPropertyChangeListener(validityChangeListener);
    }
}
