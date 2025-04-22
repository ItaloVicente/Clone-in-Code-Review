	public void testStopOnConflictAndContinue() throws Exception {
		RevCommit firstInMaster = writeFileAndCommit(FILE1
				"2"
		writeFileAndCommit(FILE1

		checkFile(FILE1
		createBranch(firstInMaster
		checkoutBranch("refs/heads/topic");
		checkFile(FILE1

		writeFileAndCommit(FILE1
				"3"

		writeFileAndCommit(FILE1
				"change file1 in topic\n\nThis is conflicting"
				"3"

		writeFileAndCommit(FILE1
				"2topic"

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
		assertEquals(Status.OK
		assertEquals(RepositoryState.SAFE

		ObjectId headId = db.resolve(Constants.HEAD);
		RevWalk rw = new RevWalk(db);
		RevCommit rc = rw.parseCommit(headId);
		RevCommit parent = rw.parseCommit(rc.getParent(0));
		assertEquals("change file1 in topic\n\nThis is conflicting"
				.getFullMessage());
	}

	public void testStopOnConflictAndFailContinueIfFileIsDirty()
			throws Exception {
		RevCommit firstInMaster = writeFileAndCommit(FILE1
				"2"
		writeFileAndCommit(FILE1

		checkFile(FILE1
		createBranch(firstInMaster
		checkoutBranch("refs/heads/topic");
		checkFile(FILE1

		writeFileAndCommit(FILE1
				"3"

		writeFileAndCommit(FILE1
				"change file1 in topic\n\nThis is conflicting"
				"3"

		writeFileAndCommit(FILE1
				"2topic"

		RebaseResult res = git.rebase().setUpstream("refs/heads/master").call();
		assertEquals(Status.STOPPED

		git.add().addFilepattern(FILE1).call();
		File trashFile = writeTrashFile(FILE1

		res = git.rebase().setOperation(Operation.CONTINUE).call();
		assertNotNull(res);
		assertEquals(Status.STOPPED
		checkFile(trashFile
	}

	public void testStopOnLastConflictAndContinue() throws Exception {
		RevCommit firstInMaster = writeFileAndCommit(FILE1
				"2"
		writeFileAndCommit(FILE1

		checkFile(FILE1
		createBranch(firstInMaster
		checkoutBranch("refs/heads/topic");
		checkFile(FILE1

		writeFileAndCommit(FILE1
				"3"

		writeFileAndCommit(FILE1
				"change file1 in topic\n\nThis is conflicting"
				"3"

		RebaseResult res = git.rebase().setUpstream("refs/heads/master").call();
		assertEquals(Status.STOPPED

		writeFileAndAdd(FILE1

		res = git.rebase().setOperation(Operation.CONTINUE).call();
		assertNotNull(res);
		assertEquals(Status.OK
		assertEquals(RepositoryState.SAFE
	}

	public void testStopOnLastConflictAndSkip() throws Exception {
		RevCommit firstInMaster = writeFileAndCommit(FILE1
				"2"
		writeFileAndCommit(FILE1

		checkFile(FILE1
		createBranch(firstInMaster
		checkoutBranch("refs/heads/topic");
		checkFile(FILE1

		writeFileAndCommit(FILE1
				"3"

		writeFileAndCommit(FILE1
				"change file1 in topic\n\nThis is conflicting"
				"3"

		RebaseResult res = git.rebase().setUpstream("refs/heads/master").call();
		assertEquals(Status.STOPPED

		writeFileAndAdd(FILE1

		res = git.rebase().setOperation(Operation.SKIP).call();
		assertNotNull(res);
		assertEquals(Status.OK
		assertEquals(RepositoryState.SAFE
	}

	public void testStopOnConflictAndSkipNoConflict() throws Exception {
		RevCommit firstInMaster = writeFileAndCommit(FILE1
				"2"
		writeFileAndCommit(FILE1

		checkFile(FILE1
		createBranch(firstInMaster
		checkoutBranch("refs/heads/topic");
		checkFile(FILE1

		writeFileAndCommit(FILE1
				"3"

		writeFileAndCommit(FILE1
				"change file1 in topic\n\nThis is conflicting"
				"3"

		writeFileAndCommit(FILE1
				"3topic"

		RebaseResult res = git.rebase().setUpstream("refs/heads/master").call();
		assertEquals(Status.STOPPED

		res = git.rebase().setOperation(Operation.SKIP).call();

		checkFile(FILE1
		assertEquals(Status.OK
	}

	public void testStopOnConflictAndSkipWithConflict() throws Exception {
		RevCommit firstInMaster = writeFileAndCommit(FILE1
				"2"
		writeFileAndCommit(FILE1
				"3master"

		checkFile(FILE1
		createBranch(firstInMaster
		checkoutBranch("refs/heads/topic");
		checkFile(FILE1

		writeFileAndCommit(FILE1
				"3"

		writeFileAndCommit(FILE1
				"change file1 in topic\n\nThis is conflicting"
				"3"

		writeFileAndCommit(FILE1
				"3topic"

		RebaseResult res = git.rebase().setUpstream("refs/heads/master").call();
		assertEquals(Status.STOPPED

		res = git.rebase().setOperation(Operation.SKIP).call();
		checkFile(FILE1
				"1master\n2\n<<<<<<< OURS\n3master\n=======\n3topic\n>>>>>>> THEIRS\n4\n5topic");
		assertEquals(Status.STOPPED
	}

	public void testStopOnConflictCommitAndContinue() throws Exception {
		RevCommit firstInMaster = writeFileAndCommit(FILE1
				"2"
		writeFileAndCommit(FILE1

		checkFile(FILE1
		createBranch(firstInMaster
		checkoutBranch("refs/heads/topic");
		checkFile(FILE1

		writeFileAndCommit(FILE1
				"3"

		writeFileAndCommit(FILE1
				"change file1 in topic\n\nThis is conflicting"
				"3"

		writeFileAndCommit(FILE1
				"3topic"

		RebaseResult res = git.rebase().setUpstream("refs/heads/master").call();
		assertEquals(Status.STOPPED

		try {
			res = git.rebase().setOperation(Operation.CONTINUE).call();
			fail("Expected Exception not thrown");
		} catch (UnmergedPathsException e) {
		}

		writeFileAndCommit(FILE1
				"3"

		res = git.rebase().setOperation(Operation.CONTINUE).call();
		assertNotNull(res);
		assertEquals(Status.OK
		assertEquals(RepositoryState.SAFE

		ObjectId headId = db.resolve(Constants.HEAD);
		RevWalk rw = new RevWalk(db);
		RevCommit rc = rw.parseCommit(headId);
		RevCommit parent = rw.parseCommit(rc.getParent(0));
		assertEquals("A different commit message"
	}

	private RevCommit writeFileAndCommit(String fileName
			String... lines) throws Exception {
		StringBuilder sb = new StringBuilder();
		for (String line : lines) {
			sb.append(line);
			sb.append('\n');
		}
		writeTrashFile(fileName
		git.add().addFilepattern(fileName).call();
		return git.commit().setMessage(commitMessage).call();
	}
