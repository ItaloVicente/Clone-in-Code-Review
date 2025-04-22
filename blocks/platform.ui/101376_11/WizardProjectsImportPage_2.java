		Button closeProjectsCheckbox = new Button(optionsGroup, SWT.CHECK);
		closeProjectsCheckbox.setText(DataTransferMessages.WizardProjectsImportPage_closeProjectsAfterImport);
		closeProjectsCheckbox.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		closeProjectsCheckbox.setSelection(closeProjectsAfterImport);
		closeProjectsCheckbox.addSelectionListener(
				SelectionListener.widgetSelectedAdapter(e -> closeProjectsAfterImport = closeProjectsCheckbox.getSelection()));

