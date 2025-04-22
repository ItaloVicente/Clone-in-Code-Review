		Button openProjectsCheckbox = new Button(optionsGroup, SWT.CHECK);
		openProjectsCheckbox.setText(DataTransferMessages.WizardProjectsImportPage_openProjects);
		openProjectsCheckbox.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		openProjectsCheckbox.setSelection(openProjects);
		openProjectsCheckbox.addSelectionListener(
				SelectionListener.widgetSelectedAdapter(e -> openProjects = openProjectsCheckbox.getSelection()));

