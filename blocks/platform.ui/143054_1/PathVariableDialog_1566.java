		Composite parentComposite = (Composite) super.createDialogArea(parent);

		initializeDialogUnits(parentComposite);

		Composite contents = createComposite(parentComposite);

		createWidgets(contents);

		if (type == EXISTING_VARIABLE) {
			nameEntered = locationEntered = true;
			validateVariableValue();
		}

		Dialog.applyDialogFont(parentComposite);

		return contents;
	}

	private Composite createComposite(Composite parentComposite) {
		Composite contents = new Composite(parentComposite, SWT.NONE);

		contents.setLayout(new GridLayout(3, false));
		contents.setLayoutData(new GridData(GridData.FILL_BOTH));

		switch (operationMode) {
		case NEW_VARIABLE:
			setTitle(IDEWorkbenchMessages.PathVariableDialog_dialogTitle_newVariable);
			break;
		case EXISTING_VARIABLE:
			setTitle(IDEWorkbenchMessages.PathVariableDialog_dialogTitle_existingVariable);
			break;
		default:
			setTitle(IDEWorkbenchMessages.PathVariableDialog_dialogTitle_editLinkLocation);
			break;
		}
		setMessage(standardMessage);
		return contents;
	}

	private void createWidgets(Composite contents) {
		String nameLabelText = IDEWorkbenchMessages.PathVariableDialog_variableName;
		String valueLabelText = IDEWorkbenchMessages.PathVariableDialog_variableValue;
		String resolvedValueLabelText = IDEWorkbenchMessages.PathVariableDialog_variableResolvedValue;

		if (operationMode != EDIT_LINK_LOCATION) {
			variableNameLabel = new Label(contents, SWT.LEAD);
			variableNameLabel.setText(nameLabelText);
			variableNameLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));

			variableNameField = new Text(contents, SWT.SINGLE | SWT.BORDER);
			variableNameField.setText(variableName);
			variableNameField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
					false, 2, 1));
			variableNameField.addModifyListener(event -> variableNameModified());
		}

		variableValueLabel = new Label(contents, SWT.LEAD);
		variableValueLabel.setText(valueLabelText);
		variableValueLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));

		variableValueField = new Text(contents, SWT.SINGLE | SWT.BORDER);
		variableValueField.setText(variableValue);
		variableValueField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false));
		variableValueField.addModifyListener(event -> variableValueModified());

		Composite buttonsComposite = new Composite(contents, SWT.NONE);
		buttonsComposite.setLayoutData(new GridData(SWT.END, SWT.CENTER, false,
				false, 1, 1));
		GridLayout layout = new GridLayout(0, true);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		buttonsComposite.setLayout(layout);

		if ((variableType & IResource.FILE) != 0) {
			layout.numColumns++;
			fileButton = new Button(buttonsComposite, SWT.PUSH);
			fileButton.setText(IDEWorkbenchMessages.PathVariableDialog_file);
			fileButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
					false));

			fileButton.addSelectionListener(new SelectionAdapter() {
				@Override
