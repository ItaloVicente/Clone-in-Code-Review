		Button importClosedCheckbox = new Button(parent, SWT.CHECK);
		importClosedCheckbox.setText(DataTransferMessages.SmartImportWizardPage_importClosed);
		importClosedCheckbox.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1));
		importClosedCheckbox.addSelectionListener(
				SelectionListener.widgetSelectedAdapter(e -> importClosed = importClosedCheckbox.getSelection()));

