		GridData gd;
		Composite content = new Composite(parent, SWT.NONE);
		setControl(content);
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		content.setLayout(layout);
		applyDialogFont(content);

		initializeDialogUnits(content);

		descriptionLabel = createDescriptionLabel(content);
		if (descriptionLabel != null) {
			descriptionLabel.setLayoutData(new GridData(
					GridData.FILL_HORIZONTAL));
		}

		body = createContents(content);
		if (body != null) {
