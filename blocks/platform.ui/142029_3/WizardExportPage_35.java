		updateWidgetEnablements();
		setPageComplete(determinePageCompletion());

		setControl(composite);
	}

	protected abstract void createDestinationGroup(Composite parent);

	protected final void createSourceGroup(Composite parent) {
		Composite sourceGroup = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		sourceGroup.setLayout(layout);
		sourceGroup.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL | GridData.HORIZONTAL_ALIGN_FILL));

		new Label(sourceGroup, SWT.NONE).setText(IDEWorkbenchMessages.WizardExportPage_folder);

		resourceNameField = new Text(sourceGroup, SWT.SINGLE | SWT.BORDER);
		resourceNameField.addListener(SWT.KeyDown, this);
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL);
		data.widthHint = SIZING_TEXT_FIELD_WIDTH;
		resourceNameField.setLayoutData(data);

		resourceBrowseButton = new Button(sourceGroup, SWT.PUSH);
		resourceBrowseButton.setText(IDEWorkbenchMessages.WizardExportPage_browse);
		resourceBrowseButton.addListener(SWT.Selection, this);
		resourceBrowseButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL));

		exportAllTypesRadio = new Button(sourceGroup, SWT.RADIO);
		exportAllTypesRadio.setText(IDEWorkbenchMessages.WizardExportPage_allTypes);
		exportAllTypesRadio.addListener(SWT.Selection, this);
		data = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL);
		data.horizontalSpan = 3;
		exportAllTypesRadio.setLayoutData(data);

		exportSpecifiedTypesRadio = new Button(sourceGroup, SWT.RADIO);
		exportSpecifiedTypesRadio.setText(IDEWorkbenchMessages.WizardExportPage_specificTypes);
		exportSpecifiedTypesRadio.addListener(SWT.Selection, this);

		typesToExportField = new Combo(sourceGroup, SWT.NONE);
		data = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL);
		data.widthHint = SIZING_TEXT_FIELD_WIDTH;
		typesToExportField.setLayoutData(data);
		typesToExportField.addListener(SWT.Modify, this);

		typesToExportEditButton = new Button(sourceGroup, SWT.PUSH);
		typesToExportEditButton.setText(IDEWorkbenchMessages.WizardExportPage_edit);
		typesToExportEditButton.setLayoutData(
				new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL | GridData.VERTICAL_ALIGN_END));
		typesToExportEditButton.addListener(SWT.Selection, this);

		resourceDetailsButton = new Button(sourceGroup, SWT.PUSH);
		resourceDetailsButton.setText(IDEWorkbenchMessages.WizardExportPage_details);
		resourceDetailsButton.addListener(SWT.Selection, this);

		resourceDetailsDescription = new Label(sourceGroup, SWT.NONE);
		data = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL);
		data.horizontalSpan = 2;
		resourceDetailsDescription.setLayoutData(data);

		resetSelectedResources();
		exportAllTypesRadio.setSelection(initialExportAllTypesValue);
		exportSpecifiedTypesRadio.setSelection(!initialExportAllTypesValue);
		typesToExportField.setEnabled(!initialExportAllTypesValue);
		typesToExportEditButton.setEnabled(!initialExportAllTypesValue);

		if (initialExportFieldValue != null) {
