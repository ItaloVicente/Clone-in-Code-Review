	@Test
	public void testSuccessfulMergeFailsDueToDirtyIndex() throws Exception {
		Git git = new Git(db);

		RevCommit firstMasterCommit = createAddAndCommitFileA(git);
		RevCommit sideCommit = createAndCheckoutSideBranchAndAddFileB(
				firstMasterCommit
		checkoutMasterBranchAndAddFileC(git

		writeTrashFile("a"
		git.add().addFilepattern("a").call();

		String indexState = indexState(CONTENT);

		MergeResult result = git.merge().include(sideCommit.getId())
				.setStrategy(MergeStrategy.RESOLVE).call();

		checkMergeFailedResult(result
	}

	@Test
	public void testConflictingMergeFailsDueToDirtyIndex() throws Exception {
		Git git = new Git(db);

		RevCommit firstMasterCommit = createAddAndCommitFileA(git);
		RevCommit sideCommit = createAndCheckoutSideBranchAndAddFileB(
				firstMasterCommit
		checkoutMasterBranchAndAddFileC(git

		writeTrashFile("a"
		git.add().addFilepattern("a").call();

		String indexState = indexState(CONTENT);

		MergeResult result = git.merge().include(sideCommit.getId())
				.setStrategy(MergeStrategy.RESOLVE).call();

		checkMergeFailedResult(result
	}

	@Test
	public void testSuccessfulMergeFailsDueToDirtyWorktree() throws Exception {
		Git git = new Git(db);

		RevCommit firstMasterCommit = createAddAndCommitFileA(git);
		RevCommit sideCommit = createAndCheckoutSideBranchAndAddFileB(
				firstMasterCommit
		checkoutMasterBranchAndAddFileC(git

		writeTrashFile("a"

		String indexState = indexState(CONTENT);

		MergeResult result = git.merge().include(sideCommit.getId())
				.setStrategy(MergeStrategy.RESOLVE).call();

		checkMergeFailedResult(result
	}

	@Test
	public void testConflictingMergeFailsDueToDirtyWorktree() throws Exception {
		Git git = new Git(db);

		RevCommit firstMasterCommit = createAddAndCommitFileA(git);
		RevCommit sideCommit = createAndCheckoutSideBranchAndAddFileB(
				firstMasterCommit
		checkoutMasterBranchAndAddFileC(git

		writeTrashFile("a"

		String indexState = indexState(CONTENT);

		MergeResult result = git.merge().include(sideCommit.getId())
				.setStrategy(MergeStrategy.RESOLVE).call();

		checkMergeFailedResult(result
	}

	private RevCommit createAddAndCommitFileA(final Git git) throws Exception {
		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		return git.commit().setMessage("message").call();
	}

	private RevCommit createAndCheckoutSideBranchAndAddFileB(
			final RevCommit commit
			throws Exception {
		createBranch(commit
		checkoutBranch("refs/heads/side");

		if (modifyFileA)
			writeTrashFile("a"
		writeTrashFile("b"
		git.add().addFilepattern("a").addFilepattern("b").call();
		return git.commit().setMessage("message").call();
	}

	private RevCommit checkoutMasterBranchAndAddFileC(final Git git
			final boolean modifyFileA) throws Exception {
		checkoutBranch("refs/heads/master");

		if (modifyFileA)
			writeTrashFile("a"
		writeTrashFile("c"
		git.add().addFilepattern("a").addFilepattern("c").call();
		return git.commit().setMessage("message").call();
	}

	private void checkMergeFailedResult(final MergeResult result
			final String indexState) throws Exception {
		assertEquals(MergeStatus.FAILED
		assertEquals("a(modified)"
		assertFalse(new File(db.getWorkTree()
		assertEquals("c"
		assertEquals(indexState
		assertEquals(null
		assertEquals(RepositoryState.SAFE
	}

