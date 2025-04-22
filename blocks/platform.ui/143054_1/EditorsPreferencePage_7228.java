	protected void createSpace(Composite parent) {
		WorkbenchPreferencePage.createSpace(parent);
	}

	protected void createShowMultipleEditorTabsPref(Composite composite) {
		showMultipleEditorTabs = new Button(composite, SWT.CHECK);
		showMultipleEditorTabs.setText(WorkbenchMessages.WorkbenchPreference_showMultipleEditorTabsButton);
		showMultipleEditorTabs.setSelection(
				getAPIPreferenceStore().getBoolean(IWorkbenchPreferenceConstants.SHOW_MULTIPLE_EDITOR_TABS));
		setButtonLayoutData(showMultipleEditorTabs);
	}

	protected void createAllowInplaceEditorPref(Composite composite) {
		allowInplaceEditor = new Button(composite, SWT.CHECK);
		allowInplaceEditor.setText(WorkbenchMessages.WorkbenchPreference_allowInplaceEditingButton);
		allowInplaceEditor.setSelection(
				!getAPIPreferenceStore().getBoolean(IWorkbenchPreferenceConstants.DISABLE_OPEN_EDITOR_IN_PLACE));
		setButtonLayoutData(allowInplaceEditor);
	}

	protected void createUseIPersistablePref(Composite composite) {
		useIPersistableEditor = new Button(composite, SWT.CHECK);
		useIPersistableEditor.setText(WorkbenchMessages.WorkbenchPreference_useIPersistableEditorButton);
		useIPersistableEditor
				.setSelection(getPreferenceStore().getBoolean(IPreferenceConstants.USE_IPERSISTABLE_EDITORS));
		setButtonLayoutData(useIPersistableEditor);
	}

	protected void createPromptWhenStillOpenPref(Composite composite) {
		promptWhenStillOpenEditor = new Button(composite, SWT.CHECK);
		promptWhenStillOpenEditor.setText(WorkbenchMessages.WorkbenchPreference_promptWhenStillOpenButton);
		promptWhenStillOpenEditor.setSelection(
				getAPIPreferenceStore().getBoolean(IWorkbenchPreferenceConstants.PROMPT_WHEN_SAVEABLE_STILL_OPEN));
		setButtonLayoutData(promptWhenStillOpenEditor);
	}

	protected Composite createComposite(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		return composite;
	}

	@Override
