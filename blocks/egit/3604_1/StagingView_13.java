	void updateCommitMessageComponent(boolean repositoryChanged) {
		CommitHelper helper = new CommitHelper(currentRepository);
		CommitMessageComponentState oldState = null;
		if (repositoryChanged) {
			if (userEnteredCommmitMessage())
				saveCommitMessageComponentState();
			else
				deleteCommitMessageComponentState();
			oldState = loadCommitMessageComponentState();
			commitMessageComponent.setRepository(currentRepository);
			if (oldState == null) {
				loadInitialState(helper);
			} else {
				loadExistingState(helper, oldState);
			}
		} else {
			if (userEnteredCommmitMessage()) {
				if (!commitMessageComponent.getHeadCommit().equals(helper.getPreviousCommit()))
					addHeadChangedWarning(commitMessageComponent.getCommitMessage());
			} else
				loadInitialState(helper);
		}
		amendPreviousCommitButton.setSelection(commitMessageComponent
				.isAmending());
		amendPreviousCommitButton.setEnabled(amendAllowed(helper));
	}
