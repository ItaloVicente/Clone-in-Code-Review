	protected void createRefreshWorkspaceOnStartupPref(Composite composite) {
		refreshButton = new Button(composite, SWT.CHECK);
		refreshButton.setText(IDEWorkbenchMessages.StartupPreferencePage_refreshButton);
		refreshButton.setFont(composite.getFont());
		refreshButton.setSelection(getIDEPreferenceStore().getBoolean(
				IDEInternalPreferences.REFRESH_WORKSPACE_ON_STARTUP));
	}
