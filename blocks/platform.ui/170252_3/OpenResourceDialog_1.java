		Button okButton = getOkButton();
		if (isButtonReady(okButton) && isButtonReady(showInButton) && isButtonReady(openWithButton)) {
			if (status.matches(IStatus.ERROR) || getSelectedItems().size() == 0) {
				okButton.setEnabled(false);
				openWithButton.setEnabled(false);
				showInButton.setEnabled(false);
			} else if (selectionContainsProjects()) {
				okButton.setEnabled(false);
				openWithButton.setEnabled(false);
				showInButton.setEnabled(true);
			} else {
				okButton.setEnabled(true);
				openWithButton.setEnabled(getSelectedItems().size() == 1);
				showInButton.setEnabled(true);
			}
		}
	}

	private boolean isButtonReady(Button button) {
		return button != null && !button.isDisposed();
	}

	private boolean selectionContainsProjects() {
		for (Object item : getSelectedItems()) {
			if (item instanceof IProject) {
				return true;
			}
