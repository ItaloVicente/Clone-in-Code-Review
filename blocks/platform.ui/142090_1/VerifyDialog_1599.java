		Composite parentComposite = (Composite) super.createDialogArea(parent);

		Composite composite = new Composite(parentComposite, SWT.NONE);
		composite.setSize(SIZING_WIDTH, SWT.DEFAULT);
		GridLayout layout = new GridLayout();
		layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));

		createTestSelectionGroup(composite);
		createCheckListGroup(composite);

		_queryLabel = new Label(composite, SWT.NONE);
		_queryLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		initializeTest();
		return composite;
	}

	private void createTestSelectionGroup(Composite parent) {
		Group group = new Group(parent, SWT.SHADOW_NONE);
		group.setText("Testing:");
		group.setLayout(new GridLayout());
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		group.setLayoutData(data);
