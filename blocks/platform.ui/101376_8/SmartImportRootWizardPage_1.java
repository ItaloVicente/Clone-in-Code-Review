		Button openProjectsCheckbox = new Button(parent, SWT.CHECK);
		openProjectsCheckbox.setText(DataTransferMessages.SmartImportWizardPage_openProjects);
		openProjectsCheckbox.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1));
		openProjectsCheckbox.setSelection(openProjects);
		openProjectsCheckbox.addSelectionListener(
				SelectionListener.widgetSelectedAdapter(e -> openProjects = openProjectsCheckbox.getSelection()));

