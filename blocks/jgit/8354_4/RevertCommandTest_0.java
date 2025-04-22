
	@Test
	public void testRevertDirtyIndex() throws Exception {
		Git git = new Git(db);
		RevCommit sideCommit = prepareRevert(git);

		writeTrashFile("a"
		git.add().addFilepattern("a").call();

		doRevertAndCheckResult(git
				MergeFailureReason.DIRTY_INDEX);
	}

	@Test
	public void testRevertDirtyWorktree() throws Exception {
		Git git = new Git(db);
		RevCommit sideCommit = prepareRevert(git);

		writeTrashFile("a"

		doRevertAndCheckResult(git
				MergeFailureReason.DIRTY_WORKTREE);
	}

	@Test
	public void testRevertConflictResolution() throws Exception {
		Git git = new Git(db);
		RevCommit sideCommit = prepareRevert(git);

		RevertCommand revert = git.revert();
		RevCommit newHead = revert.include(sideCommit.getId()).call();
		assertNull(newHead);
		MergeResult result = revert.getFailingResult();
		assertEquals(MergeStatus.CONFLICTING
		assertTrue(new File(db.getDirectory()
		assertEquals("Revert \"" + sideCommit.getShortMessage()
				+ "\"\n\nThis reverts commit " + sideCommit.getId().getName()
				+ ".\n"
				db.readMergeCommitMsg());
		assertTrue(new File(db.getDirectory()
				.exists());
		assertEquals(sideCommit.getId()
		assertEquals(RepositoryState.REVERTING

		writeTrashFile("a"
		git.add().addFilepattern("a").call();

		assertEquals(RepositoryState.REVERTING_RESOLVED
				db.getRepositoryState());

		git.commit().setOnly("a").setMessage("resolve").call();

		assertEquals(RepositoryState.SAFE
	}

	@Test
	public void testRevertkConflictReset() throws Exception {
		Git git = new Git(db);

		RevCommit sideCommit = prepareRevert(git);

		RevertCommand revert = git.revert();
		RevCommit newHead = revert.include(sideCommit.getId()).call();
		assertNull(newHead);
		MergeResult result = revert.getFailingResult();

		assertEquals(MergeStatus.CONFLICTING
		assertEquals(RepositoryState.REVERTING
		assertTrue(new File(db.getDirectory()
				.exists());

		git.reset().setMode(ResetType.MIXED).setRef("HEAD").call();

		assertEquals(RepositoryState.SAFE
		assertFalse(new File(db.getDirectory()
				.exists());
	}

	@Test
	public void testRevertOverExecutableChangeOnNonExectuableFileSystem()
			throws Exception {
		Git git = new Git(db);
		File file = writeTrashFile("test.txt"
		assertNotNull(git.add().addFilepattern("test.txt").call());
		assertNotNull(git.commit().setMessage("commit1").call());

		assertNotNull(git.checkout().setCreateBranch(true).setName("a").call());

		writeTrashFile("test.txt"
		assertNotNull(git.add().addFilepattern("test.txt").call());
		RevCommit commit2 = git.commit().setMessage("commit2").call();
		assertNotNull(commit2);

		assertNotNull(git.checkout().setName(Constants.MASTER).call());

		DirCache cache = db.lockDirCache();
		cache.getEntry("test.txt").setFileMode(FileMode.EXECUTABLE_FILE);
		cache.write();
		assertTrue(cache.commit());
		cache.unlock();

		assertNotNull(git.commit().setMessage("commit3").call());

		db.getFS().setExecute(file
		git.getRepository()
				.getConfig()
				.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
						ConfigConstants.CONFIG_KEY_FILEMODE

		RevertCommand revert = git.revert();
		RevCommit newHead = revert.include(commit2).call();
		assertNotNull(newHead);
	}

	@Test
	public void testRevertConflictMarkers() throws Exception {
		Git git = new Git(db);
		RevCommit sideCommit = prepareRevert(git);

		RevertCommand revert = git.revert();
		RevCommit newHead = revert.include(sideCommit.getId())
				.call();
		assertNull(newHead);
		MergeResult result = revert.getFailingResult();
		assertEquals(MergeStatus.CONFLICTING

		String expected = "<<<<<<< HEAD\na(latest)\n=======\na\n>>>>>>> ca96c31 second master\n";
		checkFile(new File(db.getWorkTree()
	}

	private RevCommit prepareRevert(final Git git) throws Exception {
		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		git.commit().setMessage("first master").call();

		checkoutBranch("refs/heads/master");
		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		RevCommit oldCommit = git.commit().setMessage("second master").call();

		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		git.commit().setMessage("side").call();

		return oldCommit;
	}

	private void doRevertAndCheckResult(final Git git
			final RevCommit sideCommit
			throws Exception {
		String indexState = indexState(CONTENT);

		RevertCommand revert = git.revert();
		RevCommit resultCommit = revert.include(sideCommit.getId()).call();
		assertNull(resultCommit);
		MergeResult result = revert.getFailingResult();
		assertEquals(MergeStatus.FAILED
		assertEquals(1
		assertEquals(reason
		assertEquals("a(modified)"
		assertEquals(indexState
		assertEquals(RepositoryState.SAFE

		if (reason == null) {
			ReflogReader reader = db.getReflogReader(Constants.HEAD);
			assertTrue(reader.getLastEntry().getComment()
					.startsWith("revert: "));
			reader = db.getReflogReader(db.getBranch());
			assertTrue(reader.getLastEntry().getComment()
					.startsWith("revert: "));
		}
	}
