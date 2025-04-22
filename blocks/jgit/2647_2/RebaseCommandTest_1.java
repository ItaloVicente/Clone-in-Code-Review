	@Test
	public void testRebaseWithUntrackedFile() throws Exception {
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		RevCommit commit = git.commit().setMessage("commit1").call();

		createBranch(commit
		checkoutBranch(REFS_HEADS_TOPIC);
		writeTrashFile(FILE2
		git.add().addFilepattern(FILE2).call();
		git.commit().setMessage("commit2").call();

		checkoutBranch(REFS_HEADS_MASTER);
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("commit3").call();

		checkoutBranch(REFS_HEADS_TOPIC);
		writeTrashFile("file3"

		assertEquals(Status.OK
				.call().getStatus());
	}

	@Test
	@SuppressWarnings("null")
	public void testRebaseWithUnstagedTopicChange() throws Exception {
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		RevCommit commit = git.commit().setMessage("commit1").call();

		createBranch(commit
		checkoutBranch(REFS_HEADS_TOPIC);
		writeTrashFile(FILE2
		git.add().addFilepattern(FILE2).call();
		git.commit().setMessage("commit2").call();

		checkoutBranch(REFS_HEADS_MASTER);
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("commit3").call();

		checkoutBranch(REFS_HEADS_TOPIC);
		writeTrashFile(FILE2

		JGitInternalException exception = null;
		try {
			git.rebase().setUpstream(REFS_HEADS_MASTER).call();
		} catch (JGitInternalException e) {
			exception = e;
		}
		assertNotNull(exception);
		assertEquals("Checkout conflict with files: \nfile2"
				exception.getMessage());
	}

	@Test
	@SuppressWarnings("null")
	public void testRebaseWithUncommittedTopicChange() throws Exception {
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		RevCommit commit = git.commit().setMessage("commit1").call();

		createBranch(commit
		checkoutBranch(REFS_HEADS_TOPIC);
		writeTrashFile(FILE2
		git.add().addFilepattern(FILE2).call();
		git.commit().setMessage("commit2").call();

		checkoutBranch(REFS_HEADS_MASTER);
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("commit3").call();

		checkoutBranch(REFS_HEADS_TOPIC);
		writeTrashFile(FILE2
		git.add().addFilepattern(FILE2).call();

		JGitInternalException exception = null;
		try {
			git.rebase().setUpstream(REFS_HEADS_MASTER).call();
		} catch (JGitInternalException e) {
			exception = e;
		}
		assertNotNull(exception);
		assertEquals("Checkout conflict with files: \nfile2"
				exception.getMessage());
	}

	@Test
	@SuppressWarnings("null")
	public void testRebaseWithUnstagedMasterChange() throws Exception {
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		RevCommit commit = git.commit().setMessage("commit1").call();

		createBranch(commit
		checkoutBranch(REFS_HEADS_TOPIC);
		writeTrashFile(FILE2
		git.add().addFilepattern(FILE2).call();
		git.commit().setMessage("commit2").call();

		checkoutBranch(REFS_HEADS_MASTER);
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("commit3").call();

		checkoutBranch(REFS_HEADS_TOPIC);
		writeTrashFile(FILE1

		JGitInternalException exception = null;
		try {
			git.rebase().setUpstream(REFS_HEADS_MASTER).call();
		} catch (JGitInternalException e) {
			exception = e;
		}
		assertNotNull(exception);
		assertEquals("Checkout conflict with files: \nfile1"
				exception.getMessage());
	}

	@Test
	@SuppressWarnings("null")
	public void testRebaseWithUncommittedMasterChange() throws Exception {
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		RevCommit commit = git.commit().setMessage("commit1").call();

		createBranch(commit
		checkoutBranch(REFS_HEADS_TOPIC);
		writeTrashFile(FILE2
		git.add().addFilepattern(FILE2).call();
		git.commit().setMessage("commit2").call();

		checkoutBranch(REFS_HEADS_MASTER);
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("commit3").call();

		checkoutBranch(REFS_HEADS_TOPIC);
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();

		JGitInternalException exception = null;
		try {
			git.rebase().setUpstream(REFS_HEADS_MASTER).call();
		} catch (JGitInternalException e) {
			exception = e;
		}
		assertNotNull(exception);
		assertEquals("Checkout conflict with files: \nfile1"
				exception.getMessage());
	}

	@Test
	public void testRebaseWithUnstagedMasterChangeBaseCommit() throws Exception {
		writeTrashFile(FILE0
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE0).addFilepattern(FILE1).call();
		RevCommit commit = git.commit().setMessage("commit1").call();

		createBranch(commit
		checkoutBranch(REFS_HEADS_TOPIC);
		writeTrashFile(FILE2
		git.add().addFilepattern(FILE2).call();
		git.commit().setMessage("commit2").call();

		checkoutBranch(REFS_HEADS_MASTER);
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("commit3").call();

		checkoutBranch(REFS_HEADS_TOPIC);
		writeTrashFile(FILE0

		assertEquals(Status.OK
				.call().getStatus());
	}

	@Test
	public void testRebaseWithUncommittedMasterChangeBaseCommit()
			throws Exception {
		File file0 = writeTrashFile(FILE0
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE0).addFilepattern(FILE1).call();
		RevCommit commit = git.commit().setMessage("commit1").call();

		createBranch(commit
		checkoutBranch(REFS_HEADS_TOPIC);
		writeTrashFile(FILE2
		git.add().addFilepattern(FILE2).call();
		git.commit().setMessage("commit2").call();

		checkoutBranch(REFS_HEADS_MASTER);
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("commit3").call();

		checkoutBranch(REFS_HEADS_TOPIC);
		write(file0
		git.add().addFilepattern(FILE0).call();

		String indexState = indexState(MOD_TIME | SMUDGE | LENGTH | CONTENT_ID
				| CONTENT | ASSUME_UNCHANGED);

		RebaseResult result = git.rebase().setUpstream(REFS_HEADS_MASTER)
				.call();
		assertEquals(Status.FAILED
		assertTrue(result.getCause() instanceof AbnormalMergeFailureException);
		assertEquals(1
				.getFailingPaths().size());
		assertEquals(MergeFailureReason.DIRTY_INDEX
				((AbnormalMergeFailureException) result.getCause())
						.getFailingPaths().get(FILE0));
		assertEquals("unstaged modified file0"
		assertEquals(indexState
				| CONTENT_ID | CONTENT | ASSUME_UNCHANGED));
		assertEquals(RepositoryState.SAFE
	}

	@Test
	public void testRebaseWithUnstagedMasterChangeOtherCommit()
			throws Exception {
		writeTrashFile(FILE0
		git.add().addFilepattern(FILE0).call();
		git.commit().setMessage("commit0").call();
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		RevCommit commit = git.commit().setMessage("commit1").call();

		createBranch(commit
		checkoutBranch(REFS_HEADS_TOPIC);
		writeTrashFile(FILE2
		git.add().addFilepattern(FILE2).call();
		git.commit().setMessage("commit2").call();

		checkoutBranch(REFS_HEADS_MASTER);
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("commit3").call();

		checkoutBranch(REFS_HEADS_TOPIC);
		writeTrashFile(FILE0

		assertEquals(Status.OK
				.call().getStatus());
	}

	@Test
	public void testRebaseWithUncommittedMasterChangeOtherCommit()
			throws Exception {
		File file0 = writeTrashFile(FILE0
		git.add().addFilepattern(FILE0).call();
		git.commit().setMessage("commit0").call();
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		RevCommit commit = git.commit().setMessage("commit1").call();

		createBranch(commit
		checkoutBranch(REFS_HEADS_TOPIC);
		writeTrashFile(FILE2
		git.add().addFilepattern(FILE2).call();
		git.commit().setMessage("commit2").call();

		checkoutBranch(REFS_HEADS_MASTER);
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("commit3").call();

		checkoutBranch(REFS_HEADS_TOPIC);
		write(file0
		git.add().addFilepattern(FILE0).call();

		String indexState = indexState(MOD_TIME | SMUDGE | LENGTH | CONTENT_ID
				| CONTENT | ASSUME_UNCHANGED);

		RebaseResult result = git.rebase().setUpstream(REFS_HEADS_MASTER)
				.call();
		assertEquals(Status.FAILED
		assertTrue(result.getCause() instanceof AbnormalMergeFailureException);
		assertEquals(1
				.getFailingPaths().size());
		assertEquals(MergeFailureReason.DIRTY_INDEX
				((AbnormalMergeFailureException) result.getCause())
						.getFailingPaths().get(FILE0));
		assertEquals("unstaged modified file0"
		assertEquals(indexState
				| CONTENT_ID | CONTENT | ASSUME_UNCHANGED));
		assertEquals(RepositoryState.SAFE
	}

