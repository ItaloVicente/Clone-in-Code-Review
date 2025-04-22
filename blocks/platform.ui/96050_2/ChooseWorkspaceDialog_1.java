	private void createPromptForCopyPreferences(Composite parent) {
		Composite panel = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		panel.setLayout(layout);

		Button promptUserToCopyPreferencesButton = new Button(panel, SWT.CHECK);
		promptUserToCopyPreferencesButton
				.setText(IDEWorkbenchMessages.ChooseWorkspaceDialog_promptToImportPreferencesButtonLabel);

		Link link = new Link(panel, SWT.WRAP);
		link.setText("<a>" + "No workspace selected" + "</a>"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		link.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ImportPreferencesDialog copyPreferencesDialog = new ImportPreferencesDialog(getShell(), launchData);
				copyPreferencesDialog.open();
			}
		});
	}
