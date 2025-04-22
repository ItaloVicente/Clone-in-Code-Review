		updateUIFromState(true);
	}

	public void updateUIFromState(boolean withCommitMessage) {
		if (withCommitMessage) {
			commitText.setText(commitMessage);
			commitText.getTextWidget().setCaretOffset(caretPosition);
		}
