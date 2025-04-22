		List<GitModelObjectContainer> result = new ArrayList<GitModelObjectContainer>();
		Repository repo = gsd.getRepository();
		RevCommit srcRevCommit = gsd.getSrcRevCommit();
		RevCommit dstRevCommit = gsd.getDstRevCommit();
		TreeFilter pathFilter = gsd.getPathFilter();
		List<Commit> commitCache;
		if (srcRevCommit != null && dstRevCommit != null)
			try {
				commitCache = GitCommitsModelCache.build(repo, srcRevCommit,
						dstRevCommit, pathFilter);
			} catch (IOException e) {
				Activator.logError(e.getMessage(), e);
