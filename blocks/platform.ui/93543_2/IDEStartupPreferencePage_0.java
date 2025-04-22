	protected void createProblemsViewOnStartupPref(Composite composite) {
		showProblemsButton = new Button(composite, SWT.CHECK);
		showProblemsButton.setText(IDEWorkbenchMessages.StartupPreferencePage_showProblemsButton);
		showProblemsButton.setFont(composite.getFont());
		showProblemsButton
				.setSelection(getIDEPreferenceStore()
						.getBoolean(IDEInternalPreferences.SHOW_PROBLEMS_VIEW_DECORATIONS_ON_STARTUP));
	}

