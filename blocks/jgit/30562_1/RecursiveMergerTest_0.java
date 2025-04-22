	@Theory
	public void crissCrossMerge_ParentsNotMergeable(MergeStrategy strategy
			IndexState indexState
			throws Exception {
		if (!validateStates(indexState
			return;

		BranchBuilder master = db_t.branch("master");
		RevCommit m0 = master.commit().add("f"
				.create();
		RevCommit m1 = master.commit().add("f"
				.message("m1").create();
		db_t.getRevWalk().parseCommit(m1);

		BranchBuilder side = db_t.branch("side");
		RevCommit s1 = side.commit().parent(m0)
				.add("f"
				.message("s1").create();
		RevCommit s2 = side.commit().parent(m1)
				.add("f"
				.message("s2(merge)")
				.create();
		RevCommit m2 = master.commit().parent(s1)
				.add("f"
				.create();

		Git git = Git.wrap(db);
		git.checkout().setName("master").call();
		modifyWorktree(worktreeState
		modifyIndex(indexState

		ResolveMerger merger = (ResolveMerger) strategy.newMerger(db
				worktreeState == WorktreeState.Bare);
		if (worktreeState != WorktreeState.Bare)
			merger.setWorkingTreeIterator(new FileTreeIterator(db));
		try {
			boolean expectSuccess = true;
			if (!(indexState == IndexState.Bare
					|| indexState == IndexState.Missing || indexState == IndexState.SameAsHead))
				expectSuccess = false;
			else if (worktreeState == WorktreeState.DifferentFromHeadAndOther
					|| worktreeState == WorktreeState.SameAsOther)
				expectSuccess = false;
			assertEquals("Merge didn't returned as expected: strategy:"
					+ strategy.getName() + "
					+ "
					Boolean.valueOf(expectSuccess)
					Boolean.valueOf(merger.merge(new RevCommit[] { m2
			assertEquals(MergeStrategy.RECURSIVE
			if (!expectSuccess)
				return;
			assertEquals("1\nx(side)\n2\n3\ny(side-again)"
					contentAsString(db
			if (indexState != IndexState.Bare)
				assertEquals(
						"[f
						indexState(RepositoryTestCase.CONTENT));
			if (worktreeState != WorktreeState.Bare
					&& worktreeState != WorktreeState.Missing)
				assertEquals("1\nx(side)\n2\n3\ny(side-again)\n"
		} catch (NoMergeBaseException e) {
			assertEquals(MergeStrategy.RESOLVE
			assertEquals(e.getReason()
					MergeBaseFailureReason.MULTIPLE_MERGE_BASES_NOT_SUPPORTED);
		}
	}

