	protected void createAutoSavePref(Composite composite) {
		autoSaveButton = new Button(composite, SWT.CHECK);
		autoSaveButton.setText(IDEWorkbenchMessages.IDEWorkspacePreference_autoSaveEditors);
		autoSaveButton.setToolTipText(IDEWorkbenchMessages.IDEWorkspacePreference_autoSaveEditorsToolTip);
		autoSaveButton
.setSelection(getIDEPreferenceStore().getBoolean(IDEInternalPreferences.SAVE_AUTOMATICALLY));
	}

