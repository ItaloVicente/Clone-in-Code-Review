		final Composite customButtonBar = new Composite(parent, SWT.NONE);

		int horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		GridLayoutFactory.swtDefaults().numColumns(2).equalWidth(false)
				.spacing(horizontalSpacing, 0).applyTo(customButtonBar);

		GridDataFactory.swtDefaults().grab(true, false)
				.align(SWT.FILL, SWT.BOTTOM).applyTo(customButtonBar);

		customButtonBar.setFont(parent.getFont());

		rememberOptionsButton = new Button(customButtonBar, SWT.CHECK);
		rememberOptionsButton.setText(UIText.FinishFeatureDialog_saveAsDefault);


		int horizontlIndent = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		GridDataFactory.swtDefaults().grab(true, true)
				.align(SWT.FILL, SWT.CENTER).indent(horizontlIndent, 0)
				.applyTo(rememberOptionsButton);

		final Control buttonControl = super.createButtonBar(customButtonBar);
		GridDataFactory.swtDefaults().grab(true, false)
				.align(SWT.RIGHT, SWT.CENTER).applyTo(buttonControl);


		return customButtonBar;
