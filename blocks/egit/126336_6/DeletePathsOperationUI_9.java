	private String[] getButtonLabels() {
		return new String[] { UIText.DeletePathsOperationUI_ButtonOK, IDialogConstants.CANCEL_LABEL };
	}

	private boolean openConfirm(Shell parent, String title, String message) {
		MessageDialog dialog = new MessageDialog(parent, title, null, message,
				MessageDialog.CONFIRM, 0, getButtonLabels());
		return dialog.open() == 0;
	}

