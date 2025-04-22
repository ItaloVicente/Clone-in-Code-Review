		Composite personArea = new Composite(container, SWT.NONE);
		GridLayoutFactory.fillDefaults().spacing(3, 3).numColumns(2)
				.applyTo(personArea);
		GridDataFactory.fillDefaults().grab(true, false).span(2, 1)
				.applyTo(personArea);

		new Label(personArea, SWT.LEFT).setText(UIText.CommitDialog_Author);
		authorText = new Text(personArea, SWT.BORDER);
		authorText.setLayoutData(GridDataFactory.fillDefaults()
				.grab(true, false).create());
