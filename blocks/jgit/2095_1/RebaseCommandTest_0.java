	public void testMergeFirstStopOnLastConflictAndSkip() throws Exception {
		RevCommit firstInMaster = writeFileAndCommit(FILE1
				"2"
		writeFileAndCommit(FILE1

		checkFile(FILE1
		createBranch(firstInMaster
		checkoutBranch("refs/heads/topic");
		checkFile(FILE1

		writeFileAndCommit(FILE1
				"2"

		writeFileAndCommit(FILE1
				"change file1 in topic\n\nThis is conflicting"
				"2"

		RebaseResult res = git.rebase().setUpstream("refs/heads/master").call();
		assertEquals(Status.STOPPED

		writeFileAndAdd(FILE1

		res = git.rebase().setOperation(Operation.CONTINUE).call();
		assertEquals(Status.STOPPED

		res = git.rebase().setOperation(Operation.SKIP).call();
		assertNotNull(res);
		assertEquals(Status.OK
		assertEquals(RepositoryState.SAFE
		checkFile(FILE1
	}

