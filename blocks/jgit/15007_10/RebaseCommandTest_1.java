		assertEquals("1111111"
		assertEquals("2222222"
	}

	@Test
	public void testRebaseShouldNotFailIfUserAddCommentLinesInPrepareSteps()
			throws Exception {
		commitFile(FILE1
		RevCommit c2 = commitFile("file2"

		commitFile(FILE1
		RevCommit c4 = commitFile("file2"

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
