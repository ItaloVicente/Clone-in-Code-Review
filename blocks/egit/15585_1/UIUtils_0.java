
	public static GridData setButtonLayoutData(Button button) {
		button.setFont(JFaceResources.getDialogFont());

		GC gc = new GC(button);
		gc.setFont(button.getFont());
		FontMetrics fontMetrics = gc.getFontMetrics();
		gc.dispose();

		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		int widthHint = Dialog.convertHorizontalDLUsToPixels(fontMetrics,
				IDialogConstants.BUTTON_WIDTH);
		Point minSize = button.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
		data.widthHint = Math.max(widthHint, minSize.x);
		button.setLayoutData(data);
		return data;
	}
