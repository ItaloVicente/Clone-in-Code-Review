		if (useDefault) {
			this.buildList.setItems(getDefaultProjectOrder());
		} else {
			this.buildList.setItems(buildOrder);
		}

		return composite;

	}

	private void createSpacer(Composite composite) {
		Label spacer = new Label(composite, SWT.NONE);
		GridData spacerData = new GridData();
		spacerData.horizontalSpan = 2;
		spacer.setLayoutData(spacerData);
	}

	private void createDefaultPathButton(Composite composite, boolean selected) {

		defaultOrderInitiallySelected = selected;

		this.defaultOrderButton = new Button(composite, SWT.LEFT | SWT.CHECK);
		this.defaultOrderButton.setSelection(selected);
		this.defaultOrderButton.setText(DEFAULTS_LABEL);
		SelectionListener listener = new SelectionAdapter() {
			@Override
