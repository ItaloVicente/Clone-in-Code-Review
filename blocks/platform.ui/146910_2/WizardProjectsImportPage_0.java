				SelectionListener.widgetSelectedAdapter(e -> closeProjectsCheckboxClicked()));

		rebuildProjectsCheckbox = new Button(optionsGroup, SWT.CHECK);
		rebuildProjectsCheckbox.setText(DataTransferMessages.WizardProjectsImportPage_rebuildProjectsAfterImport);
		rebuildProjectsCheckbox.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		rebuildProjectsAfterImport = getRebuildProjectAfterImportPreference();
		rebuildProjectsCheckbox.setSelection(rebuildProjectsAfterImport);
		rebuildProjectsCheckbox.addSelectionListener(SelectionListener
				.widgetSelectedAdapter(e -> rebuildProjectsAfterImport = rebuildProjectsCheckbox.getSelection()));
