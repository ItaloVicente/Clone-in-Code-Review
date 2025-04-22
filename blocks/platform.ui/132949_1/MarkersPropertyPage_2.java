
		Composite textContainer = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		layout.marginHeight = layout.marginWidth = 0;
		textContainer.setLayout(layout);
		textContainer.setLayoutData(new GridData(GridData.FILL_BOTH));
		descriptionText = new Text(textContainer, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.widthHint = gridData.heightHint = 0;
		gridData.grabExcessHorizontalSpace = true;
