		children = gitCommit.getChildren();
	}

	public GitCommitMapping(GitModelCache gitCache) {
		super(gitCache);
		children = gitCache.getChildren();
