	@Test
	public void testPostCommitRunHook() throws Exception {
		assumeSupportedPlatform();

		writeHookFile(PostCommitHook.NAME
				"#!/bin/sh\necho \"test $1 $2\"\nread INPUT\necho $INPUT\necho 1>&2 \"stderr\"");
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayOutputStream err = new ByteArrayOutputStream();
		ProcessResult res = FS.DETECTED.runHookIfPresent(db
				PostCommitHook.NAME
				new String[] {
				"arg1"
				new PrintStream(out)

		assertEquals("unexpected hook output"
				out.toString("UTF-8"));
		assertEquals("unexpected output on stderr stream"
				err.toString("UTF-8"));
		assertEquals("unexpected exit code"
		assertEquals("unexpected process status"
				res.getStatus());
	}

	@Test
	public void testAllCommitHooks() throws Exception {
		assumeSupportedPlatform();

		writeHookFile(PreCommitHook.NAME
				"#!/bin/sh\necho \"test pre-commit\"\n\necho 1>&2 \"stderr pre-commit\"\nexit 0");
		writeHookFile(CommitMsgHook.NAME
				"#!/bin/sh\necho \"test commit-msg $1\"\n\necho 1>&2 \"stderr commit-msg\"\nexit 0");
		writeHookFile(PostCommitHook.NAME
				"#!/bin/sh\necho \"test post-commit\"\necho 1>&2 \"stderr post-commit\"\nexit 0");
		Git git = Git.wrap(db);
		String path = "a.txt";
		writeTrashFile(path
		git.add().addFilepattern(path).call();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			git.commit().setMessage("commit")
					.setHookOutputStream(new PrintStream(out)).call();
		} catch (AbortedByHookException e) {
			fail("unexpected hook failure");
		}
		assertEquals("unexpected hook output"
				"test pre-commit\ntest commit-msg .git/COMMIT_EDITMSG\ntest post-commit\n"
				out.toString("UTF-8"));
	}

