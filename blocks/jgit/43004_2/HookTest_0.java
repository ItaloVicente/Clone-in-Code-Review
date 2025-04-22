	@Test
	public void testFailedCommitMsgHookBlocksCommit() throws Exception {
		assumeSupportedPlatform();

		writeHookFile(CommitMsgHook.NAME
				"#!/bin/sh\necho \"test\"\n\necho 1>&2 \"stderr\"\nexit 1");
		Git git = Git.wrap(db);
		String path = "a.txt";
		writeTrashFile(path
		git.add().addFilepattern(path).call();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			git.commit().setMessage("commit")
					.setHookOutputStream(new PrintStream(out)).call();
			fail("expected commit-msg hook to abort commit");
		} catch (AbortedByHookException e) {
			assertEquals("unexpected error message from commit-msg hook"
					"Rejected by \"commit-msg\" hook.\nstderr\n"
					e.getMessage());
			assertEquals("unexpected output from commit-msg hook"
					out.toString());
		}
	}

	@Test
	public void testCommitMsgHookReceivesCorrectParameter() throws Exception {
		assumeSupportedPlatform();

		writeHookFile(CommitMsgHook.NAME
				"#!/bin/sh\necho $1\n\necho 1>&2 \"stderr\"\nexit 0");
		Git git = Git.wrap(db);
		String path = "a.txt";
		writeTrashFile(path
		git.add().addFilepattern(path).call();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		git.commit().setMessage("commit")
				.setHookOutputStream(new PrintStream(out)).call();
		assertEquals(".git/COMMIT_EDITMSG\n"
	}

	@Test
	public void testCommitMsgHookCanModifyCommitMessage() throws Exception {
		assumeSupportedPlatform();

		writeHookFile(CommitMsgHook.NAME
				"#!/bin/sh\necho \"new message\" > $1\nexit 0");
		Git git = Git.wrap(db);
		String path = "a.txt";
		writeTrashFile(path
		git.add().addFilepattern(path).call();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		RevCommit revCommit = git.commit().setMessage("commit")
				.setHookOutputStream(new PrintStream(out)).call();
		assertEquals("new message\n"
	}

