		final Composite customButtonBar = new Composite(parent, SWT.NONE);

		int horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		GridLayoutFactory.swtDefaults().numColumns(2).equalWidth(false)
				.spacing(horizontalSpacing, 0).applyTo(customButtonBar);

		GridDataFactory.swtDefaults().grab(true, false)
				.align(SWT.FILL, SWT.BOTTOM).applyTo(customButtonBar);

		customButtonBar.setFont(parent.getFont());

		rememberOptionsButton = new Button(customButtonBar, SWT.CHECK);
		rememberOptionsButton.setText(UIText.FinishFeatureDialog_saveAsDefault);

		final GridData leftButtonData = new GridData(SWT.LEFT, SWT.CENTER,
				true, true);
		leftButtonData.grabExcessHorizontalSpace = true;
		leftButtonData.horizontalIndent = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		rememberOptionsButton.setLayoutData(leftButtonData);

		final Control buttonControl = super.createButtonBar(customButtonBar);
		buttonControl.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true,
				false));

		return customButtonBar;
