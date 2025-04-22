	@Test
	public void testStopOnConflictAndContinueWithNoDeltaToMaster()
			throws Exception {
		RevCommit firstInMaster = writeFileAndCommit(FILE1
				"2"
		writeFileAndCommit(FILE1

		checkFile(FILE1
		createBranch(firstInMaster
		checkoutBranch("refs/heads/topic");
		checkFile(FILE1

		writeFileAndCommit(FILE1
				"change file1 in topic\n\nThis is conflicting"
				"3"

		RebaseResult res = git.rebase().setUpstream("refs/heads/master").call();
		assertEquals(Status.STOPPED

		try {
			res = git.rebase().setOperation(Operation.CONTINUE).call();
			fail("Expected Exception not thrown");
		} catch (UnmergedPathsException e) {
		}

		writeFileAndAdd(FILE1

		res = git.rebase().setOperation(Operation.CONTINUE).call();
		assertNotNull(res);
		assertEquals(Status.NOTHING_TO_COMMIT
		assertEquals(RepositoryState.REBASING_INTERACTIVE
				db.getRepositoryState());

		git.rebase().setOperation(Operation.SKIP).call();

		ObjectId headId = db.resolve(Constants.HEAD);
		RevWalk rw = new RevWalk(db);
		RevCommit rc = rw.parseCommit(headId);
		assertEquals("change file1 in master"
	}

