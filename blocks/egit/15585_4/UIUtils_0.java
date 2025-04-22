
	public static void setButtonLayoutData(Button button) {
		GC gc = new GC(button);
		gc.setFont(JFaceResources.getDialogFont());
		FontMetrics fontMetrics = gc.getFontMetrics();
		gc.dispose();

		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		int widthHint = Dialog.convertHorizontalDLUsToPixels(fontMetrics,
				IDialogConstants.BUTTON_WIDTH);
		Point minSize = button.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
		data.widthHint = Math.max(widthHint, minSize.x);
		button.setLayoutData(data);
	}
