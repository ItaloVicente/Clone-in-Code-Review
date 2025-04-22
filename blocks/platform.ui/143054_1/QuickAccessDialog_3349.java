		GridLayoutFactory.fillDefaults().extendedMargins(isWin32 ? 0 : 3, 3, 2, 2).applyTo(composite);
		Label hintText = contents.createHintText(composite, SWT.DEFAULT);
		GridData gridData = new GridData(SWT.FILL, SWT.DEFAULT, true, false,
				((GridLayout) composite.getLayout()).numColumns, 1);
		gridData.horizontalIndent = IDialogConstants.HORIZONTAL_MARGIN;
		hintText.setLayoutData(gridData);
