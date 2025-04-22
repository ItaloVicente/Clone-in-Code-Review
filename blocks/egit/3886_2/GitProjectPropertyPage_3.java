		Text text = createText(parent);
		return text;
	}

	private Hyperlink createHeadHyperLink(Composite composite,
			String labelText) {
		createLabel(composite, labelText);

		Hyperlink hyperlink = new Hyperlink(composite, SWT.NONE);
		hyperlink.setLayoutData(GridDataFactory.fillDefaults().create());
		return hyperlink;
	}

	private Text createText(Composite parent) {
		GridData data = new GridData();
