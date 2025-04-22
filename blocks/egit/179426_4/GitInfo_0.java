	enum Source {
		WORKING_TREE, INDEX, COMMIT
	}

	Source getSource();

	AnyObjectId getCommitId();

	default GitItemState getGitState() {
		return GitItemStateFactory.getInstance().get(getRepository(),
				getGitPath());
	}
