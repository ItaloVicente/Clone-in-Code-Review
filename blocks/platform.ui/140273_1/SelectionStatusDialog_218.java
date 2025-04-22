	}

	protected void updateStatus(IStatus status) {
		fLastStatus = status;
		if (fStatusLine != null && !fStatusLine.isDisposed()) {
			updateButtonsEnableState(status);
			fStatusLine.setErrorStatus(status);
		}
	}

	protected void updateButtonsEnableState(IStatus status) {
		Button okButton = getOkButton();
		if (okButton != null && !okButton.isDisposed()) {
