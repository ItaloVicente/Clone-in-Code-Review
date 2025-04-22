		Composite group = new Composite(contents, SWT.SHADOW_NONE);
		data = new GridData();
		data.grabExcessHorizontalSpace = true;
		data.horizontalAlignment = SWT.FILL;
		data.horizontalSpan = 2;
		group.setLayout(new RowLayout(SWT.HORIZONTAL));
		group.setLayoutData(data);

		internalButton = new Button(group, SWT.RADIO | SWT.LEFT);
