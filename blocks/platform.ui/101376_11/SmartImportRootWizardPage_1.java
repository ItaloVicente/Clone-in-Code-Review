		Button closeProjectsCheckbox = new Button(parent, SWT.CHECK);
		closeProjectsCheckbox.setText(DataTransferMessages.SmartImportWizardPage_closeProjectsAfterImport);
		closeProjectsCheckbox.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1));
		closeProjectsCheckbox.setSelection(closeProjectsAfterImport);
		closeProjectsCheckbox.addSelectionListener(SelectionListener
				.widgetSelectedAdapter(e -> closeProjectsAfterImport = closeProjectsCheckbox.getSelection()));

