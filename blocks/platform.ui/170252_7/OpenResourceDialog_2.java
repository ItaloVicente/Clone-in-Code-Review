		Button okButton = getOkButton();
		if (isButtonReady(okButton) && isButtonReady(showInButton) && isButtonReady(openWithButton)) {
			if (status.matches(IStatus.ERROR) || getSelectedItems().size() == 0) {
				okButton.setEnabled(false);
				openWithButton.setEnabled(false);
				showInButton.setEnabled(false);
			} else if (getSelectedItems().toList().stream().allMatch(IFile.class::isInstance)) {
				okButton.setEnabled(true);
				openWithButton.setEnabled(getSelectedItems().size() == 1);
				showInButton.setEnabled(true);
			} else {
				okButton.setEnabled(false);
				openWithButton.setEnabled(false);
				showInButton.setEnabled(true);
			}
