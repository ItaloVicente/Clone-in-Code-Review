				.applyTo(commitMessageComposite);

		warningLabel = new ToggleableWarningLabel(commitMessageComposite,
				SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, false).exclude(true)
				.applyTo(warningLabel);

		Composite commitMessageTextComposite = toolkit
				.createComposite(commitMessageComposite);
		toolkit.paintBordersFor(commitMessageTextComposite);
		GridDataFactory.fillDefaults().grab(true, true)
				.applyTo(commitMessageTextComposite);
		GridLayoutFactory.fillDefaults().numColumns(1)
				.extendedMargins(2, 2, 2, 2)
				.applyTo(commitMessageTextComposite);
