	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, 0);
		GridLayout layout = new GridLayout();
		layout.verticalSpacing = 0;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		applyDialogFont(composite);
		initializeDialogUnits(composite);
		dialogArea = createDialogArea(composite);
		buttonBar = createButtonBar(composite);

		return composite;
	}

