	private void clearCommitMessageToggles() {
		amendPreviousCommitButton.setSelection(false);
		addChangeIdButton.setSelection(false);
		signedOffByButton.setSelection(false);
	}

	void updateCommitMessageComponent() {
		CommitHelper helper = new CommitHelper(currentRepository);
		commitMessageComponent.setAuthor(helper.getAuthor());
		commitMessageComponent
				.setCommitMessage(helper.getCannotCommitMessage());
		commitMessageComponent.setCommitter(helper.getCommitter());
		commitMessageComponent.setPreviousAuthor(helper.getPreviousAuthor());
		commitMessageComponent.setPreviousCommitMessage(helper
				.getPreviousCommitMessage());
		commitMessageComponent.updateFields();
	}

