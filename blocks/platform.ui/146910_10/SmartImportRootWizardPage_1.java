				.widgetSelectedAdapter(e -> closeProjectsCheckboxClicked(closeProjectsCheckbox.getSelection())));

		rebuildProjectsCheckbox = new Button(parent, SWT.CHECK);
		rebuildProjectsCheckbox.setText(DataTransferMessages.SmartImportWizardPage_rebuildProjectsAfterImport);
		rebuildProjectsCheckbox.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1));
		rebuildProjectsCheckbox.setSelection(rebuildProjectsAfterImport);
		rebuildProjectsCheckbox.addSelectionListener(SelectionListener
				.widgetSelectedAdapter(e -> rebuildProjectsAfterImport = rebuildProjectsCheckbox.getSelection()));
