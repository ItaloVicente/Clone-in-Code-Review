	protected void createExitPromptPref(Composite composite) {
		exitPromptButton = new Button(composite, SWT.CHECK);
		exitPromptButton.setText(IDEWorkbenchMessages.StartupPreferencePage_exitPromptButton);
		exitPromptButton.setFont(composite.getFont());
		exitPromptButton.setSelection(getIDEPreferenceStore().getBoolean(
				IDEInternalPreferences.EXIT_PROMPT_ON_CLOSE_LAST_WINDOW));
	}

	protected IPreferenceStore getIDEPreferenceStore() {
		return IDEWorkbenchPlugin.getDefault().getPreferenceStore();
	}
