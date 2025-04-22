		Composite container = new Composite(parent, SWT.NONE);
		layout = new GridLayout(1, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.verticalSpacing = pixelConverter.convertHeightInCharsToPixels(1);
		container.setLayout(layout);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Composite topGroup = new Composite(container, SWT.NONE);
		layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		topGroup.setLayout(layout);
