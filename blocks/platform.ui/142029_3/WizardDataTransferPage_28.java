	}

	protected abstract boolean allowNewContainerName();

	protected Label createBoldLabel(Composite parent, String text) {
		Label label = new Label(parent, SWT.NONE);
		label.setFont(JFaceResources.getBannerFont());
		label.setText(text);
		GridData data = new GridData();
		data.verticalAlignment = GridData.FILL;
		data.horizontalAlignment = GridData.FILL;
		label.setLayoutData(data);
		return label;
	}

	protected void createOptionsGroupButtons(Group optionsGroup) {
	}

	protected Label createPlainLabel(Composite parent, String text) {
		Label label = new Label(parent, SWT.NONE);
		label.setText(text);
		label.setFont(parent.getFont());
		GridData data = new GridData();
		data.verticalAlignment = GridData.FILL;
		data.horizontalAlignment = GridData.FILL;
		label.setLayoutData(data);
		return label;
	}

	protected void createSpacer(Composite parent) {
		Label spacer = new Label(parent, SWT.NONE);
		GridData data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.BEGINNING;
		spacer.setLayoutData(data);
	}

	protected boolean determinePageCompletion() {
		boolean complete = validateSourceGroup() && validateDestinationGroup() && validateOptionsGroup();

		if (complete) {
