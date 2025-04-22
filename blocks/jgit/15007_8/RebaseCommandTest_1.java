		assertEquals("1111111"
		assertEquals("2222222"
	}

	@Test
	public void testRebaseShouldNotFailIfUserAddCommentLinesInPrepareSteps()
			throws Exception {
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("Add file1").call();
		assertTrue(new File(db.getWorkTree()

		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		RevCommit c2 = git.commit().setMessage("Add file2").call();
		assertTrue(new File(db.getWorkTree()

		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("updated file1 on master").call();

		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		RevCommit c4 = git.commit().setMessage("update file2 on side").call();

		RebaseResult res = git.rebase().setUpstream("HEAD~2")
				.runInteractively(new InteractiveHandler() {
					public void prepareSteps(List<RebaseTodoLine> steps) {
						steps.add(0
								"# Comment that should not be processed"));
					}

					public String modifyCommitMessage(String commit) {
						fail("modifyCommitMessage() was not expected to be called");
						return commit;
					}
				}).call();

		assertEquals(RebaseResult.Status.FAST_FORWARD

		RebaseResult res2 = git.rebase().setUpstream("HEAD~2")
				.runInteractively(new InteractiveHandler() {
					public void prepareSteps(List<RebaseTodoLine> steps) {
					}

					public String modifyCommitMessage(String commit) {
						fail("modifyCommitMessage() was not expected to be called");
						return commit;
					}
				}).call();

		assertEquals(RebaseResult.Status.OK

		ObjectId headId = db.resolve(Constants.HEAD);
		RevWalk rw = new RevWalk(db);
		RevCommit rc = rw.parseCommit(headId);

		ObjectId head1Id = db.resolve(Constants.HEAD + "~1");
		RevCommit rc1 = rw.parseCommit(head1Id);

		assertEquals(rc.getFullMessage()
		assertEquals(rc1.getFullMessage()
