		Button importClosedCheckbox = new Button(optionsGroup, SWT.CHECK);
		importClosedCheckbox.setText(DataTransferMessages.WizardProjectsImportPage_importClosed);
		importClosedCheckbox.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		importClosedCheckbox.addSelectionListener(
				SelectionListener.widgetSelectedAdapter(e -> importClosed = importClosedCheckbox.getSelection()));

