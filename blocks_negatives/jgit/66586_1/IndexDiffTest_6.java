		Git git = new Git(db);

		writeTrashFile("a", "content");
		git.add().addFilepattern("a").call();
		RevCommit initialCommit = git.commit().setMessage("initial commit")
				.call();

		final String branchName = Constants.R_HEADS + "branch";
		createBranch(initialCommit, branchName);
		checkoutBranch(branchName);
		writeTrashFile("b", "second file content - branch");
		git.add().addFilepattern("b").call();
		RevCommit branchCommit = git.commit().setMessage("branch commit")
				.call();

		checkoutBranch(Constants.R_HEADS + Constants.MASTER);
		writeTrashFile("b", "second file content - master");
		git.add().addFilepattern("b").call();
		git.commit().setMessage("master commit").call();

		MergeResult result = git.merge().include(branchCommit).call();
		assertEquals(MergeStatus.CONFLICTING, result.getMergeStatus());

		FileTreeIterator iterator = new FileTreeIterator(db);
		IndexDiff diff = new IndexDiff(db, Constants.HEAD, iterator);
		diff.diff();

		assertTrue(diff.getChanged().isEmpty());
		assertTrue(diff.getAdded().isEmpty());
		assertTrue(diff.getRemoved().isEmpty());
		assertTrue(diff.getMissing().isEmpty());
		assertTrue(diff.getModified().isEmpty());
		assertEquals(1, diff.getConflicting().size());
		assertTrue(diff.getConflicting().contains("b"));
		assertEquals(StageState.BOTH_ADDED, diff.getConflictingStageStates()
				.get("b"));
		assertTrue(diff.getUntrackedFolders().isEmpty());

		writeTrashFile("b", "second file content - master");

		iterator = new FileTreeIterator(db);
		diff = new IndexDiff(db, Constants.HEAD, iterator);
		diff.diff();

		assertTrue(diff.getChanged().isEmpty());
		assertTrue(diff.getAdded().isEmpty());
		assertTrue(diff.getRemoved().isEmpty());
		assertTrue(diff.getMissing().isEmpty());
		assertTrue(diff.getModified().isEmpty());
		assertEquals(1, diff.getConflicting().size());
		assertTrue(diff.getConflicting().contains("b"));
		assertEquals(StageState.BOTH_ADDED, diff.getConflictingStageStates()
				.get("b"));
		assertTrue(diff.getUntrackedFolders().isEmpty());
