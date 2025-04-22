	protected Composite createComposite(Composite parent, int numColumns) {
		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = numColumns;
		composite.setLayout(layout);
		GridData data = new GridData();
		data.verticalAlignment = GridData.FILL;
		data.horizontalAlignment = GridData.FILL;
		composite.setLayoutData(data);
		return composite;
	}

	@Override
