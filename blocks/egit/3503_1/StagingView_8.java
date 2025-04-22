		if (amendPreviousCommitButton.getSelection())
			commitOperation.setAmending(true);
		commitOperation.setComputeChangeId(addChangeIdButton.getSelection());
		CommitUI.performCommit(currentRepository, commitOperation);
		clearCommitMessageToggles();
		commitMessageText.setText(EMPTY_STRING);
