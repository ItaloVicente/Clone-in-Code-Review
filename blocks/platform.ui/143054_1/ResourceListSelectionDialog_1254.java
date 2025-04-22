	protected void updateOKState(boolean state) {
		Button okButton = getButton(IDialogConstants.OK_ID);
		if (okButton != null && !okButton.isDisposed() && state != okEnabled) {
			okButton.setEnabled(state);
			okEnabled = state;
		}
	}
