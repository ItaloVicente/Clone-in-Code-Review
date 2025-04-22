	@Theory
	public void crissCrossMerge_twoRoots(MergeStrategy strategy
			IndexState indexState
			throws Exception {
		if (!validateStates(indexState
			return;
		BranchBuilder master = db_t.branch("master");
		BranchBuilder side = db_t.branch("side");
		RevCommit m1 = master.commit().add("m"
		db_t.getRevWalk().parseCommit(m1);

		RevCommit s1 = side.commit().add("s"
		RevCommit s2 = side.commit().parent(m1).add("m"
				.message("s2(merge)").create();
		RevCommit m2 = master.commit().parent(s1).add("s"
				.message("m2(merge)").create();

		Git git = Git.wrap(db);
		git.checkout().setName("master").call();
		modifyWorktree(worktreeState
		modifyWorktree(worktreeState
		modifyIndex(indexState
		modifyIndex(indexState

		ResolveMerger merger = (ResolveMerger) strategy.newMerger(db
				worktreeState == WorktreeState.Bare);
		if (worktreeState != WorktreeState.Bare)
			merger.setWorkingTreeIterator(new FileTreeIterator(db));
		try {
			boolean expectSuccess = true;
			if (!(indexState == IndexState.Bare
					|| indexState == IndexState.Missing
					|| indexState == IndexState.SameAsHead || indexState == IndexState.SameAsOther))
				expectSuccess = false;

			assertEquals(Boolean.valueOf(expectSuccess)
					Boolean.valueOf(merger.merge(new RevCommit[] { m2
			assertEquals(MergeStrategy.RECURSIVE
			assertEquals("m1"
					contentAsString(db
			assertEquals("s1"
					contentAsString(db
		} catch (NoMergeBaseException e) {
			assertEquals(MergeStrategy.RESOLVE
			assertEquals(e.getReason()
					MergeBaseFailureReason.MULTIPLE_MERGE_BASES_NOT_SUPPORTED);
		}
	}

