
		Composite textContainer = new Composite(composite, SWT.NONE);
		layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginHeight = layout.marginWidth = 0;
		textContainer.setLayout(layout);
		textContainer.setLayoutData(new GridData(GridData.FILL_BOTH));
		descriptionText = new Text(textContainer, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.widthHint = gridData.heightHint = 0;
		gridData.grabExcessHorizontalSpace = true;
