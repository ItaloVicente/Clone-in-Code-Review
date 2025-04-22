		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.horizontalSpacing = 0;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		composite.setFont(parent.getFont());
