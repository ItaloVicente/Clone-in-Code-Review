	protected void createOpenUnknownTextFilesInTextEditorTabsPref(Composite composite) {
		IPreferenceStore store = IDEWorkbenchPlugin.getDefault().getPreferenceStore();
		openUnknownTextFilesInTextEditor = new Button(composite, SWT.CHECK);
		openUnknownTextFilesInTextEditor
				.setText(IDEWorkbenchMessages.IDEEditorsPreferencePage_WorkbenchPreference_openUnknownTextFilesInTextEditor);
		openUnknownTextFilesInTextEditor.setSelection(store
				.getBoolean(IDE.Preferences.OPEN_UNKNOWN_TEXT_FILE_IN_TEXT_EDITOR));
		setButtonLayoutData(openUnknownTextFilesInTextEditor);
	}

	@Override
	protected void performDefaults() {
		IPreferenceStore store = IDEWorkbenchPlugin.getDefault().getPreferenceStore();
		openUnknownTextFilesInTextEditor.setSelection(store
				.getDefaultBoolean(IDE.Preferences.OPEN_UNKNOWN_TEXT_FILE_IN_TEXT_EDITOR));
		super.performDefaults();
	}

	@Override
	public boolean performOk() {
		IPreferenceStore store = IDEWorkbenchPlugin.getDefault().getPreferenceStore();
		store.setValue(IDE.Preferences.OPEN_UNKNOWN_TEXT_FILE_IN_TEXT_EDITOR,
				openUnknownTextFilesInTextEditor.getSelection());
		return super.performOk();
	}

