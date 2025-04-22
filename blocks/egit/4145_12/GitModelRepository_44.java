	private List<GitModelObjectContainer> getWorkingChanges() {
		List<GitModelObjectContainer> result = new ArrayList<GitModelObjectContainer>();
		if (gsd.shouldIncludeLocal()) {
			Repository repo = gsd.getRepository();
			GitModelCache gitCache = new GitModelCache(this, repo,
					stagedChanges);
			int gitCacheLen = gitCache.getChildren().length;

			GitModelWorkingTree gitWorkingTree = new GitModelWorkingTree(this,
					repo, workingChanges);
			int gitWorkingTreeLen = gitWorkingTree.getChildren().length;

			if (gitCacheLen > 0 || gitWorkingTreeLen > 0) {
				result.add(gitCache);
				result.add(gitWorkingTree);
