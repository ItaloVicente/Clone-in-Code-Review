		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		composite.setLayout(layout);
		GridData compositeData = new GridData(GridData.FILL_HORIZONTAL);
		compositeData.horizontalIndent = 20;
		composite.setLayoutData(compositeData);
