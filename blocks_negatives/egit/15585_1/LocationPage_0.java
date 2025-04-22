		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		int widthHint = convertHorizontalDLUsToPixels(IDialogConstants.BUTTON_WIDTH);
		Point minSize = fsBrowseButton.computeSize(SWT.DEFAULT,
				SWT.DEFAULT, true);
		data.widthHint = Math.max(widthHint, minSize.x);
		fsBrowseButton.setLayoutData(data);
