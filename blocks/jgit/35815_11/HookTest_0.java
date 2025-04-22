	@Test
	public void testFailingPreRebaseHookBlocksRebase() throws Exception {
		assumeSupportedPlatform();

		Hook h = Hook.PRE_REBASE;
		writeHookFile(h.getName()
				"#!/bin/sh\necho \"test\"\n\necho 1>&2 \"stderr\"\nexit 1");

		Git git = Git.wrap(db);
		RevCommit first = git.commit().setMessage("initial commit").call();
		createBranch(first
		git.commit().setMessage("second commit").call();
		checkoutBranch("refs/heads/branch1");

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			git.rebase().setUpstream("refs/heads/master")
					.setHookOutputStream(new PrintStream(out)).call();
			fail("expected pre-rebase hook to abort commit");
		} catch (RejectRebaseException e) {
			assertEquals("unexpected error message from pre-rebase hook"
					"Rebase rejected by \"pre-rebase\" hook.\nstderr\n"
					e.getMessage());
			assertEquals("unexpected output from pre-rebase hook"
					out.toString());
		}
	}

	@Test
	public void testFailingPreRebaseHookIgnoredWithNoVerify() throws Exception {
		assumeSupportedPlatform();

		Hook h = Hook.PRE_REBASE;
		writeHookFile(h.getName()
				"#!/bin/sh\necho \"test\"\n\necho 1>&2 \"stderr\"\nexit 1");

		Git git = Git.wrap(db);
		String patha = "a.txt";
		writeTrashFile(patha
		git.add().addFilepattern(patha).call();
		RevCommit first = git.commit().setMessage("initial commit").call();
		createBranch(first
		String pathb = "b.txt";
		writeTrashFile(pathb
		git.add().addFilepattern(pathb).call();
		git.commit().setMessage("second commit").call();
		checkoutBranch("refs/heads/branch1");

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		RebaseResult result = git.rebase().setNoVerify(true)
				.setUpstream("refs/heads/master")
				.setHookOutputStream(new PrintStream(out)).call();
		assertEquals(""
		assertEquals(Status.FAST_FORWARD
	}

	@Test
	public void testSuccessfulPreRebaseHookDoesNotBlockRebase()
			throws Exception {
		assumeSupportedPlatform();

		Hook h = Hook.PRE_REBASE;
		writeHookFile(h.getName()
				"#!/bin/sh\necho \"test\"\n\necho 1>&2 \"stderr\"\nexit 0");

		Git git = Git.wrap(db);
		String patha = "a.txt";
		writeTrashFile(patha
		git.add().addFilepattern(patha).call();
		RevCommit first = git.commit().setMessage("initial commit").call();
		createBranch(first
		String pathb = "b.txt";
		writeTrashFile(pathb
		git.add().addFilepattern(pathb).call();
		git.commit().setMessage("second commit").call();
		checkoutBranch("refs/heads/branch1");

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		RebaseResult result = git.rebase().setUpstream("refs/heads/master")
				.setHookOutputStream(new PrintStream(out)).call();
		assertEquals("test\n"
		assertEquals(Status.FAST_FORWARD
	}

