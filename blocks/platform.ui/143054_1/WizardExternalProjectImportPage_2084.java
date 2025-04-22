		initializeDialogUnits(parent);

		Composite composite = new Composite(parent, SWT.NULL);

		PlatformUI.getWorkbench().getHelpSystem().setHelp(composite, IIDEHelpContextIds.NEW_PROJECT_WIZARD_PAGE);

		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		composite.setFont(parent.getFont());

		createProjectNameGroup(composite);
		createProjectLocationGroup(composite);
		validatePage();
		setErrorMessage(null);
		setMessage(null);
		setControl(composite);
	}

	private final void createProjectLocationGroup(Composite parent) {

		Composite projectGroup = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		projectGroup.setLayout(layout);
		projectGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		projectGroup.setFont(parent.getFont());

		Label projectContentsLabel = new Label(projectGroup, SWT.NONE);
		projectContentsLabel.setText(DataTransferMessages.WizardExternalProjectImportPage_projectContentsLabel);
		projectContentsLabel.setFont(parent.getFont());

		createUserSpecifiedProjectLocationGroup(projectGroup);
	}

	private final void createProjectNameGroup(Composite parent) {

		Font dialogFont = parent.getFont();

		Composite projectGroup = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		projectGroup.setFont(dialogFont);
		projectGroup.setLayout(layout);
		projectGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Label projectLabel = new Label(projectGroup, SWT.NONE);
		projectLabel.setText(DataTransferMessages.WizardExternalProjectImportPage_nameLabel);
		projectLabel.setFont(dialogFont);

		projectNameField = new Text(projectGroup, SWT.BORDER | SWT.READ_ONLY);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = SIZING_TEXT_FIELD_WIDTH;
		projectNameField.setLayoutData(data);
		projectNameField.setFont(dialogFont);
		projectNameField.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
	}

	private void createUserSpecifiedProjectLocationGroup(Composite projectGroup) {

		Font dialogFont = projectGroup.getFont();

		this.locationPathField = new Text(projectGroup, SWT.BORDER);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = SIZING_TEXT_FIELD_WIDTH;
		this.locationPathField.setLayoutData(data);
		this.locationPathField.setFont(dialogFont);

		this.browseButton = new Button(projectGroup, SWT.PUSH);
		this.browseButton.setText(DataTransferMessages.DataTransfer_browse);
		this.browseButton.setFont(dialogFont);
		setButtonLayoutData(this.browseButton);

		this.browseButton.addSelectionListener(new SelectionAdapter() {
			@Override
