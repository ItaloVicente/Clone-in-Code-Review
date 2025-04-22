
		Composite checkboxPanel = new Composite(composite, SWT.NONE);
		checkboxPanel.setFont(parent.getFont());
		GridLayout layout = new GridLayout(1, false);
		layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		checkboxPanel.setLayout(layout);
		GridData data = new GridData(GridData.FILL_BOTH);
		data.verticalAlignment = GridData.END;
		checkboxPanel.setLayoutData(data);

