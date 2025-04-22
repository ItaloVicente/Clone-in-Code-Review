	@Test
	public void testPostRewriteHook() throws Exception {
		assumeSupportedPlatform();

		Hook h = Hook.POST_REWRITE;
		writeHookFile(h.getName()
				"#!/bin/sh\necho \"test\"\n\necho 1>&2 \"stderr\"\nexit 1");
		Git git = Git.wrap(db);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		git.commit().setAmend(true).setMessage("amended commit msg.")
				.setHookOutputStream(new PrintStream(out)).call();
		assertEquals("test\n"
	}

	@Test
	public void testPostRewriteHookCanBeIgnored() throws Exception {
		assumeSupportedPlatform();

		Hook h = Hook.POST_REWRITE;
		writeHookFile(h.getName()
				"#!/bin/sh\necho \"test\"\n\necho 1>&2 \"stderr\"\nexit 1");
		Git git = Git.wrap(db);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		git.commit().setAmend(true).setNoPostRewrite(true)
				.setMessage("amended commit msg.")
				.setHookOutputStream(new PrintStream(out)).call();
		assertEquals(""
	}

	@Test
	public void testPostRewriteHookOnRebase() throws Exception {
		assumeSupportedPlatform();

		Hook h = Hook.POST_REWRITE;
		writeHookFile(h.getName()
				"#!/bin/sh\necho \"test\"\n\necho 1>&2 \"stderr\"\nexit 1");
		Git git = Git.wrap(db);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		RevCommit first = git.commit().setMessage("test1").call();
		createBranch(first
		git.commit().setMessage("test2").call();
		checkoutBranch("refs/heads/branch1");
		git.rebase().setHookOutputStream(new PrintStream(out)).call();
		assertEquals("test\n"
	}

