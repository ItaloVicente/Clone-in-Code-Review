		Hyperlink hyperlink = new Hyperlink(composite, SWT.NONE);
		hyperlink.setLayoutData(GridDataFactory.fillDefaults().create());
		return hyperlink;
	}

	private Text createText(Composite parent) {
		GridData data = new GridData();
