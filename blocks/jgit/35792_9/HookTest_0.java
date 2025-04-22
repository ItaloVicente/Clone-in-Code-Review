	@Test
	public void testPostRewriteHookOnAmend() throws Exception {
		assumeSupportedPlatform();

		Git git = Git.wrap(db);
		String path = "a.txt";
		writeTrashFile(path
		git.add().addFilepattern(path).call();
		RevCommit toAmend = git.commit().setMessage("to amend").call();

		Hook h = Hook.POST_REWRITE;
		writeHookFile(h.getName()
				"#!/bin/sh\necho \"$1/$#\"\nwhile read line ;\necho $line;\ndone\nexit 0");
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		RevCommit amended = git.commit().setAmend(true).setMessage("amended commit msg.")
				.setHookOutputStream(new PrintStream(out)).call();
		assertEquals("amend\n" + toAmend.getId().name() + " "
				+ amended.getId().name() + "\n"
	}

	@Test
	public void testPostRewriteHookCanBeIgnored() throws Exception {
		assumeSupportedPlatform();

		Git git = Git.wrap(db);
		String path = "a.txt";
		writeTrashFile(path
		git.add().addFilepattern(path).call();
		git.commit().setMessage("to amend").call();

		Hook h = Hook.POST_REWRITE;
		writeHookFile(h.getName()
				"#!/bin/sh\necho \"test\"\nexit 1");
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
				"#!/bin/sh\necho \"$1/$#\"\nwhile read line ;\necho $line;\ndone\nexit 0");
		Git git = Git.wrap(db);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		RevCommit first = git.commit().setMessage("test1").call();
		createBranch(first.getParent(0)
		checkoutBranch("refs/heads/branch1");
		RevCommit toRebase = git.commit().setMessage("test2").call();
		git.rebase().setUpstream("refs/heads/master")
				.setHookOutputStream(new PrintStream(out)).call();
		assertEquals(
				"rebase\n" + toRebase.getId().name() + " "
						+ db.getRef("HEAD").getObjectId().name() + "\n"
				out.toString("UTF-8"));
	}

