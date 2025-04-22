		Composite composite = new Composite(parent, SWT.NONE);
		GridData cGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		cGridData.horizontalSpan = 2;
		composite.setLayoutData(cGridData);
		GridLayout layout = new GridLayout(1, false);
		layout.marginHeight = layout.marginWidth = 0;
		composite.setLayout(layout);

		Label label = new Label(composite, SWT.NONE);
