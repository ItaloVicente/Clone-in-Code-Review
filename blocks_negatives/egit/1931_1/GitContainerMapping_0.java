	public GitCommitMapping(GitModelCache gitCache) {
		super(gitCache);
		children = gitCache.getChildren();
	}

	public GitCommitMapping(GitModelWorkingTree workingTree) {
		super(workingTree);
		children = workingTree.getChildren();
	}

