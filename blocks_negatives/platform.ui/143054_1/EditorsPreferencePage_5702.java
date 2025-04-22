        return WorkbenchPlugin.getDefault().getPreferenceStore();
    }

    protected IPreferenceStore getAPIPreferenceStore() {
    	return PrefUtil.getAPIPreferenceStore();
    }

    protected void updateValidState() {
        if (!recentFilesEditor.isValid()) {
            setErrorMessage(recentFilesEditor.getErrorMessage());
            setValid(false);
        } else if (!reuseEditorsThreshold.isValid()) {
            setErrorMessage(reuseEditorsThreshold.getErrorMessage());
            setValid(false);
        } else {
            setErrorMessage(null);
            setValid(true);
        }
    }

    /**
     * Create a composite that contains entry fields specifying editor reuse preferences.
     */
    protected void createEditorReuseGroup(Composite composite) {
        editorReuseGroup = new Composite(composite, SWT.LEFT);
        GridLayout layout = new GridLayout();
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        editorReuseGroup.setLayout(layout);
        editorReuseGroup.setLayoutData(new GridData(
                GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL));

        reuseEditors = new Button(editorReuseGroup, SWT.CHECK);
        reuseEditors.setText(WorkbenchMessages.WorkbenchPreference_reuseEditors);
        reuseEditors.setLayoutData(new GridData());

        IPreferenceStore store = WorkbenchPlugin.getDefault()
                .getPreferenceStore();
        reuseEditors.setSelection(store
                .getBoolean(IPreferenceConstants.REUSE_EDITORS_BOOLEAN));
        reuseEditors.addSelectionListener(widgetSelectedAdapter(e -> {
