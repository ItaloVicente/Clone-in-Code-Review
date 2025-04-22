	@Test
	public void testParseSquashFixupSequenceCount() {
		int count = RebaseCommand
				.parseSquashFixupSequenceCount("# This is a combination of 3 commits.\n# newline");
		assertEquals(3
	}

	@Test
	public void testRebaseInteractiveSingleSquashAndModifyMessage() throws Exception {
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("Add file1\nnew line").call();
		assertTrue(new File(db.getWorkTree()

		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("Add file2\nnew line").call();
		assertTrue(new File(db.getWorkTree()

		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("updated file1 on master\nnew line").call();

		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("update file2 on side\nnew line").call();

		final File messageSquashFile = new File(db.getDirectory()
				"rebase-merge" + File.separator + "message-squash");
		final File messageFixupFile = new File(db.getDirectory()
				"rebase-merge" + File.separator + "message-fixup");

		git.rebase().setUpstream("HEAD~3")
				.runInteractively(new InteractiveHandler() {

					public void prepareSteps(List<Step> steps) {
						steps.get(1).setAction(Action.SQUASH);
					}

					public String modifyCommitMessage(String commit) {
						assertFalse(messageFixupFile.exists());
						assertTrue(messageSquashFile.exists());
						assertEquals(
								"# This is a combination of 2 commits.\n# This is the 2nd commit message:\nupdated file1 on master\nnew line\n# The first commit's message is:\nAdd file2\nnew line"
								commit);

						try {
							byte[] messageSquashBytes = IO
									.readFully(messageSquashFile);
							int end = RawParseUtils.prevLF(messageSquashBytes
									messageSquashBytes.length);
							String messageSquashContend = RawParseUtils.decode(
									messageSquashBytes
							assertEquals(messageSquashContend
						} catch (Throwable t) {
							fail(t.getMessage());
						}

						return "changed";
					}
				}).call();

		RevWalk walk = new RevWalk(db);
		ObjectId headId = db.resolve(Constants.HEAD);
		RevCommit headCommit = walk.parseCommit(headId);
		assertEquals(headCommit.getFullMessage()
				"update file2 on side\nnew line");

		ObjectId head2Id = db.resolve(Constants.HEAD + "^1");
		RevCommit head1Commit = walk.parseCommit(head2Id);
		assertEquals("changed"
	}

	@Test
	public void testRebaseInteractiveMultipleSquash() throws Exception {
		writeTrashFile("file0"
		git.add().addFilepattern("file0").call();
		git.commit().setMessage("Add file0\nnew line").call();
		assertTrue(new File(db.getWorkTree()

		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("Add file1\nnew line").call();
		assertTrue(new File(db.getWorkTree()

		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("Add file2\nnew line").call();
		assertTrue(new File(db.getWorkTree()

		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("updated file1 on master\nnew line").call();

		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("update file2 on side\nnew line").call();

		final File messageSquashFile = new File(db.getDirectory()
				"rebase-merge" + File.separator + "message-squash");
		final File messageFixupFile = new File(db.getDirectory()
				"rebase-merge" + File.separator + "message-fixup");

		git.rebase().setUpstream("HEAD~4")
				.runInteractively(new InteractiveHandler() {

					public void prepareSteps(List<Step> steps) {
						steps.get(1).setAction(Action.SQUASH);
						steps.get(2).setAction(Action.SQUASH);
					}

					public String modifyCommitMessage(String commit) {
						assertFalse(messageFixupFile.exists());
						assertTrue(messageSquashFile.exists());
						assertEquals(
								"# This is a combination of 3 commits.\n# This is the 3rd commit message:\nupdated file1 on master\nnew line\n# This is the 2nd commit message:\nAdd file2\nnew line\n# The first commit's message is:\nAdd file1\nnew line"
								commit);

						try {
							byte[] messageSquashBytes = IO
									.readFully(messageSquashFile);
							int end = RawParseUtils.prevLF(messageSquashBytes
									messageSquashBytes.length);
							String messageSquashContend = RawParseUtils.decode(
									messageSquashBytes
							assertEquals(messageSquashContend
						} catch (Throwable t) {
							fail(t.getMessage());
						}

						return "# This is a combination of 3 commits.\n# This is the 3rd commit message:\nupdated file1 on master\nnew line\n# This is the 2nd commit message:\nAdd file2\nnew line\n# The first commit's message is:\nAdd file1\nnew line";
					}
				}).call();

		RevWalk walk = new RevWalk(db);
		ObjectId headId = db.resolve(Constants.HEAD);
		RevCommit headCommit = walk.parseCommit(headId);
		assertEquals(headCommit.getFullMessage()
				"update file2 on side\nnew line");

		ObjectId head2Id = db.resolve(Constants.HEAD + "^1");
		RevCommit head1Commit = walk.parseCommit(head2Id);
		assertEquals(
				"updated file1 on master\nnew line\nAdd file2\nnew line\nAdd file1\nnew line"
				head1Commit.getFullMessage());
	}

	@Test
	public void testRebaseInteractiveMixedSquashAndFixup() throws Exception {
		writeTrashFile("file0"
		git.add().addFilepattern("file0").call();
		git.commit().setMessage("Add file0\nnew line").call();
		assertTrue(new File(db.getWorkTree()

		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("Add file1\nnew line").call();
		assertTrue(new File(db.getWorkTree()

		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("Add file2\nnew line").call();
		assertTrue(new File(db.getWorkTree()

		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("updated file1 on master\nnew line").call();

		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("update file2 on side\nnew line").call();

		final File messageSquashFile = new File(db.getDirectory()
				"rebase-merge" + File.separator + "message-squash");
		final File messageFixupFile = new File(db.getDirectory()
				"rebase-merge" + File.separator + "message-fixup");

		git.rebase().setUpstream("HEAD~4")
				.runInteractively(new InteractiveHandler() {

					public void prepareSteps(List<Step> steps) {
						steps.get(1).setAction(Action.FIXUP);
						steps.get(2).setAction(Action.SQUASH);
					}

					public String modifyCommitMessage(String commit) {
						assertFalse(messageFixupFile.exists());
						assertTrue(messageSquashFile.exists());
						assertEquals(
								"# This is a combination of 3 commits.\n# This is the 3rd commit message:\nupdated file1 on master\nnew line\n# The 2nd commit message will be skipped:\n# Add file2\n# new line\n# The first commit's message is:\nAdd file1\nnew line"
								commit);

						try {
							byte[] messageSquashBytes = IO
									.readFully(messageSquashFile);
							int end = RawParseUtils.prevLF(messageSquashBytes
									messageSquashBytes.length);
							String messageSquashContend = RawParseUtils.decode(
									messageSquashBytes
							assertEquals(messageSquashContend
						} catch (Throwable t) {
							fail(t.getMessage());
						}

						return "changed";
					}
				}).call();

		RevWalk walk = new RevWalk(db);
		ObjectId headId = db.resolve(Constants.HEAD);
		RevCommit headCommit = walk.parseCommit(headId);
		assertEquals(headCommit.getFullMessage()
				"update file2 on side\nnew line");

		ObjectId head2Id = db.resolve(Constants.HEAD + "^1");
		RevCommit head1Commit = walk.parseCommit(head2Id);
		assertEquals("changed"
	}

	@Test
	public void testRebaseInteractiveSingleFixup() throws Exception {
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("Add file1\nnew line").call();
		assertTrue(new File(db.getWorkTree()

		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("Add file2\nnew line").call();
		assertTrue(new File(db.getWorkTree()

		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("updated file1 on master\nnew line").call();

		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("update file2 on side\nnew line").call();

		git.rebase().setUpstream("HEAD~3")
				.runInteractively(new InteractiveHandler() {

					public void prepareSteps(List<Step> steps) {
						steps.get(1).setAction(Action.FIXUP);
					}

					public String modifyCommitMessage(String commit) {
						fail("No callback to modify commit message expected for single fixup");
						return commit;
					}
				}).call();

		RevWalk walk = new RevWalk(db);
		ObjectId headId = db.resolve(Constants.HEAD);
		RevCommit headCommit = walk.parseCommit(headId);
		assertEquals(headCommit.getFullMessage()
				"update file2 on side\nnew line");

		ObjectId head1Id = db.resolve(Constants.HEAD + "^1");
		RevCommit head1Commit = walk.parseCommit(head1Id);
		assertEquals("Add file2\nnew line"
				head1Commit.getFullMessage());
	}


