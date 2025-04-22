		Composite mainColumn = new Composite(advancedComposite, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		mainColumn.setFont(parent.getFont());
		mainColumn.setLayout(layout);

		GridData data = new GridData(GridData.BEGINNING);
		data.horizontalSpan = 2;
		Label label = new Label(mainColumn, SWT.LEFT);
		label.setText(RESOURCE_BUNDLE.getString("colorsAndFonts")); //$NON-NLS-1$
		myApplyDialogFont(label);
		label.setLayoutData(data);

		createTree(mainColumn);

		Composite controlColumn = new Composite(mainColumn, SWT.NONE);
		data = new GridData(GridData.FILL_VERTICAL);
		controlColumn.setLayoutData(data);
		layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		controlColumn.setLayout(layout);

		Label placeholder = new Label(controlColumn, SWT.NONE);
		GridData placeholderData = new GridData(SWT.TOP);
		placeholderData.heightHint = convertVerticalDLUsToPixels(12);
		placeholder.setLayoutData(placeholderData);
