	@Test
	public void testFailedCommitMsgHookBlocksCommit() throws Exception {
		assumeSupportedPlatform();

		Hook h = Hook.COMMIT_MSG;
		writeHookFile(h.getName()
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
		} catch (RejectCommitException e) {
			assertEquals("unexpected error message from commit-msg hook"
					"Commit rejected by \"commit-msg\" hook.\nstderr\n"
					e.getMessage());
			assertEquals("unexpected output from commit-msg hook"
					out.toString());
		}
	}

	@Test
	public void testCommitMsgHook() throws Exception {
		assumeSupportedPlatform();

		Hook h = Hook.COMMIT_MSG;
		writeHookFile(h.getName()
				"#!/bin/sh\necho \"test\"\n\necho 1>&2 \"stderr\"\nexit 0");
		Git git = Git.wrap(db);
		String path = "a.txt";
		writeTrashFile(path
		git.add().addFilepattern(path).call();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		git.commit().setMessage("commit")
				.setHookOutputStream(new PrintStream(out)).call();
		assertEquals("test\n"
	}

