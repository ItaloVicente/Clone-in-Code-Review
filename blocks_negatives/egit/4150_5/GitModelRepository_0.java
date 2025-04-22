
		Repository repo = gsd.getRepository();
		stagedChanges = StagedChangeCache.build(repo);
		workingChanges = WorkingTreeChangeCache.build(repo);

		RevCommit srcRevCommit = gsd.getSrcRevCommit();
		RevCommit dstRevCommit = gsd.getDstRevCommit();
		TreeFilter pathFilter = gsd.getPathFilter();
		if (srcRevCommit != null && dstRevCommit != null)
			commitCache = GitCommitsModelCache.build(repo, srcRevCommit,
					dstRevCommit, pathFilter);
		else
			commitCache = null;
