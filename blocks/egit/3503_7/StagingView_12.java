	private void clearCommitMessageToggles() {
		amendPreviousCommitButton.setSelection(false);
		addChangeIdButton.setSelection(false);
		signedOffByButton.setSelection(false);
	}

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
	}

	private void loadExistingState(CommitHelper helper,
			CommitMessageComponentState oldState) {
		boolean headCommitChanged = !oldState.getHeadCommit().equals(
				getCommitId(helper.getPreviousCommit()));
		commitMessageComponent.enableListers(false);
		commitMessageComponent.setAuthor(oldState.getAuthor());
		if (headCommitChanged)
			addHeadChangedWarning(oldState.getCommitMessage());
