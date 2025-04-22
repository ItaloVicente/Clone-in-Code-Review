
		Repository repo = gsd.getRepository();
		stagedChanges = StagedChangeCache.build(repo);
		workingChanges = WorkingTreeChangeCache.build(repo);

		RevCommit srcRevCommit = gsd.getSrcRevCommit();
		RevCommit dstRevCommit = gsd.getDstRevCommit();
		if (srcRevCommit != null && dstRevCommit != null)
			commitCache = CheckedInCommitsCache.build(repo, srcRevCommit,
					dstRevCommit);
		else
			commitCache = null;
