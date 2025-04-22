	private void loadInitialState(CommitHelper helper) {
		commitMessageComponent.enableListers(false);
		commitMessageComponent.resetState();
		commitMessageComponent.setAuthor(helper.getAuthor());
		commitMessageComponent.setCommitMessage(helper
				.getCommitMessage());
		commitMessageComponent.setCommitter(helper.getCommitter());
		commitMessageComponent.setHeadCommit(getCommitId(helper
				.getPreviousCommit()));
		commitMessageComponent.setAmendAllowed(true);
		commitMessageComponent.setAmending(false);
		commitMessageComponent.setSignedOff(false);
		commitMessageComponent.setCreateChangeId(false);
		commitMessageComponent.updateUI();
		commitMessageComponent.enableListers(true);
	}
