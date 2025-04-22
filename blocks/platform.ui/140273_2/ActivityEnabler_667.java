	private GridData setButtonLayoutData(Button button, FontMetrics fontMetrics) {
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		int widthHint = Dialog.convertHorizontalDLUsToPixels(fontMetrics, IDialogConstants.BUTTON_WIDTH);
		Point minSize = button.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
		data.widthHint = Math.max(widthHint, minSize.x);
		button.setLayoutData(data);
		return data;
	}
