	protected void createShowMultipleEditorTabsPref(Composite composite) {
		showMultipleEditorTabs = new Button(composite, SWT.CHECK);
		showMultipleEditorTabs.setText(WorkbenchMessages.WorkbenchPreference_showMultipleEditorTabsButton);
		showMultipleEditorTabs.setSelection(
				getAPIPreferenceStore().getBoolean(IWorkbenchPreferenceConstants.SHOW_MULTIPLE_EDITOR_TABS));
		setButtonLayoutData(showMultipleEditorTabs);
	}
