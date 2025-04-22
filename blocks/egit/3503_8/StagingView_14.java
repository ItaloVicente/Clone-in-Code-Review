	private void loadExistingState(CommitHelper helper,
			CommitMessageComponentState oldState) {
		boolean headCommitChanged = !oldState.getHeadCommit().equals(
				getCommitId(helper.getPreviousCommit()));
		commitMessageComponent.enableListers(false);
		commitMessageComponent.setAuthor(oldState.getAuthor());
		if (headCommitChanged)
			addHeadChangedWarning(oldState.getCommitMessage());
		else
			commitMessageComponent.setCommitMessage(oldState
					.getCommitMessage());
		commitMessageComponent.setCommitter(oldState.getCommitter());
		commitMessageComponent.setHeadCommit(getCommitId(helper
				.getPreviousCommit()));
		boolean amendAllowed = amendAllowed(helper);
		commitMessageComponent.setAmendAllowed(amendAllowed);
		if (!amendAllowed) {
			commitMessageComponent.setAmending(false);
		} else {
			if (!headCommitChanged && oldState.getAmend())
				commitMessageComponent.setAmending(true);
			else
				commitMessageComponent.setAmending(false);
