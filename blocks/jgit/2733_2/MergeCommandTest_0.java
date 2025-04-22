	@Test
	public void testSuccessfulMergeFailsDueToDirtyIndex() throws Exception {
		Git git = new Git(db);

		File fileA = writeTrashFile("a"
		RevCommit initialCommit = addAllAndCommit(git);

		createBranch(initialCommit
		checkoutBranch("refs/heads/side");
		write(fileA
		writeTrashFile("b"
		RevCommit sideCommit = addAllAndCommit(git);

		checkoutBranch("refs/heads/master");
		writeTrashFile("c"
		addAllAndCommit(git);

		write(fileA
		git.add().addFilepattern("a").call();

		String indexState = indexState(CONTENT);

		MergeResult result = git.merge().include(sideCommit.getId())
				.setStrategy(MergeStrategy.RESOLVE).call();

		checkMergeFailedResult(result
	}

	@Test
	public void testConflictingMergeFailsDueToDirtyIndex() throws Exception {
		Git git = new Git(db);

		File fileA = writeTrashFile("a"
		RevCommit initialCommit = addAllAndCommit(git);

		createBranch(initialCommit
		checkoutBranch("refs/heads/side");
		write(fileA
		writeTrashFile("b"
		RevCommit sideCommit = addAllAndCommit(git);

		checkoutBranch("refs/heads/master");
		write(fileA
		writeTrashFile("c"
		addAllAndCommit(git);

		write(fileA
		git.add().addFilepattern("a").call();

		String indexState = indexState(CONTENT);

		MergeResult result = git.merge().include(sideCommit.getId())
				.setStrategy(MergeStrategy.RESOLVE).call();

		checkMergeFailedResult(result
	}

	@Test
	public void testSuccessfulMergeFailsDueToDirtyWorktree() throws Exception {
		Git git = new Git(db);

		File fileA = writeTrashFile("a"
		RevCommit initialCommit = addAllAndCommit(git);

		createBranch(initialCommit
		checkoutBranch("refs/heads/side");
		write(fileA
		writeTrashFile("b"
		RevCommit sideCommit = addAllAndCommit(git);

		checkoutBranch("refs/heads/master");
		writeTrashFile("c"
		addAllAndCommit(git);

		write(fileA

		String indexState = indexState(CONTENT);

		MergeResult result = git.merge().include(sideCommit.getId())
				.setStrategy(MergeStrategy.RESOLVE).call();

		checkMergeFailedResult(result
	}

	@Test
	public void testConflictingMergeFailsDueToDirtyWorktree() throws Exception {
		Git git = new Git(db);

		File fileA = writeTrashFile("a"
		RevCommit initialCommit = addAllAndCommit(git);

		createBranch(initialCommit
		checkoutBranch("refs/heads/side");
		write(fileA
		writeTrashFile("b"
		RevCommit sideCommit = addAllAndCommit(git);

		checkoutBranch("refs/heads/master");
		write(fileA
		writeTrashFile("c"
		addAllAndCommit(git);

		write(fileA

		String indexState = indexState(CONTENT);

		MergeResult result = git.merge().include(sideCommit.getId())
				.setStrategy(MergeStrategy.RESOLVE).call();

		checkMergeFailedResult(result
	}

	private RevCommit addAllAndCommit(final Git git) throws Exception {
		git.add().addFilepattern(".").call();
		return git.commit().setMessage("message").call();
	}

	private void checkMergeFailedResult(final MergeResult result
			final String indexState
		assertEquals(MergeStatus.FAILED
		assertEquals("a(modified)"
		assertFalse(new File(db.getWorkTree()
		assertEquals("c"
		assertEquals(indexState
		assertEquals(null
		assertEquals(RepositoryState.SAFE
	}

