	private void disableCommitButton(Button button) {
		if (!button.getSelection()
				&& getProblemsSeverity() >= IMarker.SEVERITY_WARNING) {
			commitAndPushButton.setEnabled(false);
			commitButton.setEnabled(false);
		} else {
			commitAndPushButton.setEnabled(true);
			commitButton.setEnabled(true);
		}
	}

