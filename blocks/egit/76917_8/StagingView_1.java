	public void resetCommitMessageComponent() {
		if (currentRepository != null) {
			String commitMessage = commitMessageComponent.getCommitMessage();
			if (commitMessage.trim().length() > 0) {
				CommitMessageHistory.saveCommitHistory(commitMessage);
			}
			loadInitialState(new CommitHelper(currentRepository));
		}
	}

