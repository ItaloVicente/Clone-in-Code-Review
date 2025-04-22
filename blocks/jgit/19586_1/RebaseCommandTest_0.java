	@Test
	public void testStopOnConflictAndAbortWithDetachedHEAD() throws Exception {
		RevCommit firstInMaster = writeFileAndCommit(FILE1
				"2"
		writeFileAndCommit(FILE1
		checkFile(FILE1
		createBranch(firstInMaster
		checkoutBranch("refs/heads/topic");
		checkFile(FILE1

		writeFileAndCommit(FILE1
				"3"

		RevCommit conflicting = writeFileAndCommit(FILE1
				"change file1 in topic"

		RevCommit lastTopicCommit = writeFileAndCommit(FILE1
				"change file1 in topic again"

		git.checkout().setName(lastTopicCommit.getName()).call();

		RebaseResult res = git.rebase().setUpstream("refs/heads/master").call();
		assertEquals(Status.STOPPED
		assertEquals(conflicting
		checkFile(FILE1
				"<<<<<<< Upstream
				">>>>>>> e0d1dea change file1 in topic\n2\n3\ntopic4");

		assertEquals(RepositoryState.REBASING_INTERACTIVE
				db.getRepositoryState());
		assertTrue(new File(db.getDirectory()
		assertEquals(1

		try {
			git.rebase().setUpstream("refs/heads/master").call();
			fail("Expected exception was not thrown");
		} catch (WrongRepositoryStateException e) {
		}

		res = git.rebase().setOperation(Operation.ABORT).call();
		assertEquals(res.getStatus()
		assertEquals(lastTopicCommit.getName()
		checkFile(FILE1
		RevWalk rw = new RevWalk(db);
		assertEquals(lastTopicCommit
				rw.parseCommit(db.resolve(Constants.HEAD)));
		assertEquals(RepositoryState.SAFE

		assertFalse(new File(db.getDirectory()
	}

