	@Test
	public void testStageState_mergeAndReset_bug() throws Exception {
		Git git = new Git(db);

		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		RevCommit initialCommit = git.commit().setMessage("initial commit")
				.call();

		final String branchName = Constants.R_HEADS + "branch";
		createBranch(initialCommit
		checkoutBranch(branchName);
		writeTrashFile("b"
		git.add().addFilepattern("b").call();
		RevCommit branchCommit = git.commit().setMessage("branch commit")
				.call();

		checkoutBranch(Constants.R_HEADS + Constants.MASTER);
		writeTrashFile("b"
		git.add().addFilepattern("b").call();
		git.commit().setMessage("master commit").call();

		MergeResult result = git.merge().include(branchCommit).call();
		assertEquals(MergeStatus.CONFLICTING

		FileTreeIterator iterator = new FileTreeIterator(db);
		IndexDiff diff = new IndexDiff(db
		diff.diff();

		assertTrue(diff.getChanged().isEmpty());
		assertTrue(diff.getAdded().isEmpty());
		assertTrue(diff.getRemoved().isEmpty());
		assertTrue(diff.getMissing().isEmpty());
		assertTrue(diff.getModified().isEmpty());
		assertEquals(1
		assertTrue(diff.getConflicting().contains("b"));
		assertEquals(StageState.BOTH_ADDED
				.get("b"));
		assertTrue(diff.getUntrackedFolders().isEmpty());

		writeTrashFile("b"

		iterator = new FileTreeIterator(db);
		diff = new IndexDiff(db
		diff.diff();

		assertTrue(diff.getChanged().isEmpty());
		assertTrue(diff.getAdded().isEmpty());
		assertTrue(diff.getRemoved().isEmpty());
		assertTrue(diff.getMissing().isEmpty());
		assertTrue(diff.getModified().isEmpty());
		assertEquals(1
		assertTrue(diff.getConflicting().contains("b"));
		assertEquals(StageState.BOTH_ADDED
				.get("b"));
		assertTrue(diff.getUntrackedFolders().isEmpty());
	}

	@Test
	public void testStageState_simulated_bug() throws Exception {
		Git git = new Git(db);

		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		RevCommit initialCommit = git.commit().setMessage("initial commit")
				.call();

		final String branchName = Constants.R_HEADS + "branch";
		createBranch(initialCommit
		checkoutBranch(branchName);
		writeTrashFile("b"
		git.add().addFilepattern("b").call();
		git.commit().setMessage("branch commit")
				.call();

		checkoutBranch(Constants.R_HEADS + Constants.MASTER);
		writeTrashFile("b"
		git.add().addFilepattern("b").call();
		git.commit().setMessage("master commit").call();

		DirCacheBuilder builder = db.lockDirCache().builder();
		DirCacheEntry entry = createEntry("a"
				"content");
		builder.add(entry);
		entry = createEntry("b"
				"second file content - master");
		builder.add(entry);
		entry = createEntry("b"
				"second file content - branch");
		builder.add(entry);
		builder.commit();

		FileTreeIterator iterator = new FileTreeIterator(db);
		IndexDiff diff = new IndexDiff(db
		diff.diff();

		assertTrue(diff.getChanged().isEmpty());
		assertTrue(diff.getAdded().isEmpty());
		assertTrue(diff.getRemoved().isEmpty());
		assertTrue(diff.getMissing().isEmpty());
		assertTrue(diff.getModified().isEmpty());
		assertEquals(1
		assertTrue(diff.getConflicting().contains("b"));
		assertEquals(StageState.BOTH_ADDED
				.get("b"));
		assertTrue(diff.getUntrackedFolders().isEmpty());
	}

