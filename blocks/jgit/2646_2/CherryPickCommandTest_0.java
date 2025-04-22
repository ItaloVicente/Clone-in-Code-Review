
	@Test
	public void testCherryPickDirtyIndex() throws Exception {
		Git git = new Git(db);
		RevCommit sideCommit = prepareCherryPick(git);

		writeTrashFile("a"
		git.add().addFilepattern("a").call();

		doCherryPickAndCheckResult(git
				MergeFailureReason.DIRTY_INDEX);
	}

	@Test
	public void testCherryPickDirtyWorktree() throws Exception {
		Git git = new Git(db);
		RevCommit sideCommit = prepareCherryPick(git);

		writeTrashFile("a"

		doCherryPickAndCheckResult(git
				MergeFailureReason.DIRTY_WORKTREE);
	}

	private RevCommit prepareCherryPick(final Git git) throws Exception {
		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		RevCommit firstMasterCommit = git.commit().setMessage("first master")
				.call();

		createBranch(firstMasterCommit
		checkoutBranch("refs/heads/side");
		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		RevCommit sideCommit = git.commit().setMessage("side").call();

		checkoutBranch("refs/heads/master");
		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		git.commit().setMessage("second master").call();
		return sideCommit;
	}

	private void doCherryPickAndCheckResult(final Git git
			final RevCommit sideCommit
			throws Exception {
		String indexState = indexState(CONTENT);

		CherryPickResult result = git.cherryPick().include(sideCommit.getId())
				.call();
		assertEquals(CherryPickStatus.FAILED
		assertEquals(1
		assertEquals(reason
		assertEquals("a(modified)"
		assertEquals(indexState
		assertEquals(RepositoryState.SAFE
	}
