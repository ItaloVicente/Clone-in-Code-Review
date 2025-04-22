	@Test
	public void testPostCommitHook() throws Exception {
		assumeSupportedPlatform();

		Hook h = Hook.POST_COMMIT;
		writeHookFile(h.getName()
				"#!/bin/sh\necho \"test\"\n\necho 1>&2 \"stderr\"\nexit 1");
		Git git = Git.wrap(db);
		String path = "a.txt";
		writeTrashFile(path
		git.add().addFilepattern(path).call();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		git.commit().setMessage("commit")
				.setHookOutputStream(new PrintStream(out)).call();
		assertEquals("test\n"
	}

	@Test
	public void testPostCommitHookErrorHandlerIsCalledOnFailure()
			throws Exception {
		assumeSupportedPlatform();

		final Hook h = Hook.POST_COMMIT;
		writeHookFile(h.getName()
				"#!/bin/sh\necho \"test\"\n\necho 1>&2 \"stderr\"\nexit 1");
		Git git = Git.wrap(db);
		String path = "a.txt";
		writeTrashFile(path
		git.add().addFilepattern(path).call();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		TestHookErrorHandler handler = new TestHookErrorHandler();
		git.commit().setMessage("commit")
				.setHookOutputStream(new PrintStream(out))
				.setHookErrorHandler(handler).call();
		assertEquals(1
		assertEquals(1
		assertEquals("stderr\n"
		assertEquals(h
	}

	@Test
	public void testPostCommitHookErrorHandlerIsIgnoredOnSuccess()
			throws Exception {
		assumeSupportedPlatform();

		final Hook h = Hook.POST_COMMIT;
		writeHookFile(h.getName()
				"#!/bin/sh\necho \"test\"\n\necho 1>&2 \"stderr\"\nexit 0");
		Git git = Git.wrap(db);
		String path = "a.txt";
		writeTrashFile(path
		git.add().addFilepattern(path).call();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		TestHookErrorHandler handler = new TestHookErrorHandler();
		git.commit().setMessage("commit")
				.setHookOutputStream(new PrintStream(out))
				.setHookErrorHandler(handler).call();
		assertEquals(0
	}

