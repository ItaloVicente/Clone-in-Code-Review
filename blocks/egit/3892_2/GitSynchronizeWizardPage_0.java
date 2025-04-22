		final Button fetchChanges = new Button(buttonsComposite, SWT.CHECK);
		fetchChanges
		.setText(UIText.GitBranchSynchronizeWizardPage_fetchChangesFromRemote);
		fetchChanges.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				forceFetch = fetchChanges.getSelection();
			}
		});
		fetchChanges.setLayoutData(GridDataFactory.fillDefaults()
				.grab(true, false).create());
		fetchChanges.setSelection(Activator.getDefault().getPreferenceStore()
				.getBoolean(UIPreferences.SYNC_VIEW_FETCH_BEFORE_LAUNCH));

