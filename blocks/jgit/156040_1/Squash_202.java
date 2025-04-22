	public Squash(final GitImpl git
			final String squashedCommitMessage) {
		this.git = git;
		this.squashedCommitMessage = squashedCommitMessage;
		this.branch = branch;
		this.startCommitString = startCommitString;
	}
