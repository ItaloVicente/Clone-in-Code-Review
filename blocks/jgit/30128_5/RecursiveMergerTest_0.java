	@Theory
	public void crissCrossMerge_mergeable2(MergeStrategy strategy
			IndexState indexState
			throws Exception {
		if (!validateStates(indexState
			return;

		BranchBuilder master = db_t.branch("master");
		RevCommit m0 = master.commit().add("f"
				.message("m0")
				.create();
		RevCommit m1 = master.commit().add("f"
				.message("m1").create();
		db_t.getRevWalk().parseCommit(m1);

		BranchBuilder side = db_t.branch("side");
		RevCommit s1 = side.commit().parent(m0).add("f"
				.message("s1").create();
		RevCommit s2 = side.commit().parent(m1)
				.add("f"
				.message("s2(merge)")
				.create();
		RevCommit m2 = master.commit().parent(s1)
				.add("f"
				.message("m2(merge)")
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
			assertEquals(Boolean.valueOf(expectSuccess)
					Boolean.valueOf(merger.merge(new RevCommit[] { m2
			assertEquals(MergeStrategy.RECURSIVE
			if (!expectSuccess)
				return;
			assertEquals(
					"1-master-r\n2\n3-side-r"
					contentAsString(db
			if (indexState != IndexState.Bare)
				assertEquals(
						"[f
						indexState(RepositoryTestCase.CONTENT));
			if (worktreeState != WorktreeState.Bare
					&& worktreeState != WorktreeState.Missing)
				assertEquals(
						"1-master-r\n2\n3-side-r\n"
						read("f"));
		} catch (NoMergeBaseException e) {
			assertEquals(MergeStrategy.RESOLVE
			assertEquals(e.getReason()
					MergeBaseFailureReason.MULTIPLE_MERGE_BASES_NOT_SUPPORTED);
		}
	}

	@Theory
	public void crissCrossMerge_checkOtherFiles(MergeStrategy strategy
			IndexState indexState
			throws Exception {
		if (!validateStates(indexState
			return;

		BranchBuilder master = db_t.branch("master");
		RevCommit m0 = master.commit().add("f"
				.add("m.d"
				.create();
		RevCommit m1 = master.commit().add("f"
				.add("m.c"
				.create();
		db_t.getRevWalk().parseCommit(m1);

		BranchBuilder side = db_t.branch("side");
		RevCommit s1 = side.commit().parent(m0).add("f"
				.add("s.c"
				.create();
		RevCommit s2 = side.commit().parent(m1)
				.add("f"
				.add("m.c"
		RevCommit m2 = master.commit().parent(s1)
				.add("f"
				.add("s.c"

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
			assertEquals(Boolean.valueOf(expectSuccess)
					Boolean.valueOf(merger.merge(new RevCommit[] { m2
			assertEquals(MergeStrategy.RECURSIVE
			if (!expectSuccess)
				return;
			assertEquals(
					"1-master-r\n2\n3-side-r"
					contentAsString(db
			if (indexState != IndexState.Bare)
				assertEquals(
						"[f
						indexState(RepositoryTestCase.CONTENT));
			if (worktreeState != WorktreeState.Bare
					&& worktreeState != WorktreeState.Missing) {
				assertEquals(
						"1-master-r\n2\n3-side-r\n"
						read("f"));
				assertTrue(check("s.c"));
				assertFalse(check("s.d"));
				assertTrue(check("s.m"));
				assertTrue(check("m.c"));
				assertFalse(check("m.d"));
				assertTrue(check("m.m"));
			}
		} catch (NoMergeBaseException e) {
			assertEquals(MergeStrategy.RESOLVE
			assertEquals(e.getReason()
					MergeBaseFailureReason.MULTIPLE_MERGE_BASES_NOT_SUPPORTED);
		}
	}

