	private void updateCommitButtons(boolean enable) {
		if (enable) {
			commitAndPushButton.setEnabled(true);
			commitButton.setEnabled(true);
		} else {
			commitAndPushButton.setEnabled(false);
			commitButton.setEnabled(false);
		}
	}

