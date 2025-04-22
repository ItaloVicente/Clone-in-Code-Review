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

