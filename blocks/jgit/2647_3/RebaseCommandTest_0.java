	@Test
	public void testRebaseWithUntrackedFile() throws Exception {
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		RevCommit commit = git.commit().setMessage("commit1").call();

		createBranch(commit
		checkoutBranch("refs/heads/topic");
		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("commit2").call();

		checkoutBranch("refs/heads/master");
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("commit3").call();

		checkoutBranch("refs/heads/topic");
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
		checkoutBranch("refs/heads/topic");
		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("commit2").call();

		checkoutBranch("refs/heads/master");
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("commit3").call();

		checkoutBranch("refs/heads/topic");
		writeTrashFile("file2"

		JGitInternalException exception = null;
		try {
			git.rebase().setUpstream("refs/heads/master").call();
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
		checkoutBranch("refs/heads/topic");
		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("commit2").call();

		checkoutBranch("refs/heads/master");
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("commit3").call();

		checkoutBranch("refs/heads/topic");
		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();

		JGitInternalException exception = null;
		try {
			git.rebase().setUpstream("refs/heads/master").call();
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
		checkoutBranch("refs/heads/topic");
		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("commit2").call();

		checkoutBranch("refs/heads/master");
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("commit3").call();

		checkoutBranch("refs/heads/topic");
		writeTrashFile(FILE1

		JGitInternalException exception = null;
		try {
			git.rebase().setUpstream("refs/heads/master").call();
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
		checkoutBranch("refs/heads/topic");
		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("commit2").call();

		checkoutBranch("refs/heads/master");
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("commit3").call();

		checkoutBranch("refs/heads/topic");
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();

		JGitInternalException exception = null;
		try {
			git.rebase().setUpstream("refs/heads/master").call();
		} catch (JGitInternalException e) {
			exception = e;
		}
		assertNotNull(exception);
		assertEquals("Checkout conflict with files: \nfile1"
				exception.getMessage());
	}

	@Test
	public void testRebaseWithUnstagedMasterChangeBaseCommit() throws Exception {
		writeTrashFile("file0"
		writeTrashFile(FILE1
		git.add().addFilepattern("file0").addFilepattern(FILE1).call();
		RevCommit commit = git.commit().setMessage("commit1").call();

		createBranch(commit
		checkoutBranch("refs/heads/topic");
		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("commit2").call();

		checkoutBranch("refs/heads/master");
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("commit3").call();

		checkoutBranch("refs/heads/topic");
		writeTrashFile("file0"

		assertEquals(Status.OK
				.call().getStatus());
	}

	@Test
	public void testRebaseWithUncommittedMasterChangeBaseCommit()
			throws Exception {
		File file0 = writeTrashFile("file0"
		writeTrashFile(FILE1
		git.add().addFilepattern("file0").addFilepattern(FILE1).call();
		RevCommit commit = git.commit().setMessage("commit1").call();

		createBranch(commit
		checkoutBranch("refs/heads/topic");
		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("commit2").call();

		checkoutBranch("refs/heads/master");
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("commit3").call();

		checkoutBranch("refs/heads/topic");
		write(file0
		git.add().addFilepattern("file0").call();

		String indexState = indexState(CONTENT);

		RebaseResult result = git.rebase().setUpstream("refs/heads/master")
				.call();
		assertEquals(Status.FAILED
		assertEquals(1
		assertEquals(MergeFailureReason.DIRTY_INDEX
				.get("file0"));
		assertEquals("unstaged modified file0"
		assertEquals(indexState
		assertEquals(RepositoryState.SAFE
	}

	@Test
	public void testRebaseWithUnstagedMasterChangeOtherCommit()
			throws Exception {
		writeTrashFile("file0"
		git.add().addFilepattern("file0").call();
		git.commit().setMessage("commit0").call();
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		RevCommit commit = git.commit().setMessage("commit1").call();

		createBranch(commit
		checkoutBranch("refs/heads/topic");
		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("commit2").call();

		checkoutBranch("refs/heads/master");
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("commit3").call();

		checkoutBranch("refs/heads/topic");
		writeTrashFile("file0"

		assertEquals(Status.OK
				.call().getStatus());
	}

	@Test
	public void testRebaseWithUncommittedMasterChangeOtherCommit()
			throws Exception {
		File file0 = writeTrashFile("file0"
		git.add().addFilepattern("file0").call();
		git.commit().setMessage("commit0").call();
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		RevCommit commit = git.commit().setMessage("commit1").call();

		createBranch(commit
		checkoutBranch("refs/heads/topic");
		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("commit2").call();

		checkoutBranch("refs/heads/master");
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("commit3").call();

		checkoutBranch("refs/heads/topic");
		write(file0
		git.add().addFilepattern("file0").call();

		String indexState = indexState(CONTENT);

		RebaseResult result = git.rebase().setUpstream("refs/heads/master")
				.call();
		assertEquals(Status.FAILED
		assertEquals(1
		assertEquals(MergeFailureReason.DIRTY_INDEX
				.get("file0"));
		assertEquals("unstaged modified file0"
		assertEquals(indexState
		assertEquals(RepositoryState.SAFE
	}

