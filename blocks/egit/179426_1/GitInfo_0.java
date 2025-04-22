	AnyObjectId getCommitId();

	default GitItemState getGitState() {
		return GitItemStateFactory.getInstance().get(getRepository(),
				getGitPath());
	}
