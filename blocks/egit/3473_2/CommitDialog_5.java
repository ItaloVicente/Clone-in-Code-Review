		ToolBar messageToolbar = new ToolBar(container, SWT.FLAT | SWT.VERTICAL);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.TOP)
				.applyTo(messageToolbar);

		Composite personArea = new Composite(container, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(personArea);
		GridDataFactory.fillDefaults().grab(true, false).span(1, 1)
				.applyTo(personArea);

		new Label(personArea, SWT.LEFT).setText(UIText.CommitDialog_Author);
		authorText = new Text(personArea, SWT.BORDER);
		authorText.setLayoutData(GridDataFactory.fillDefaults()
				.grab(true, false).create());
