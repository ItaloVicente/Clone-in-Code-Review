	@Test
	public void testPreCommitHook() throws Exception {
		assumeSupportedPlatform();

		Hook h = Hook.PRE_COMMIT;
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
			fail("expected pre-commit hook to abort commit");
		} catch (RejectCommitException e) {
			assertEquals("unexpected error message from pre-commit hook"
					"Commit rejected by \"pre-commit\" hook.\nstderr\n"
					e.getMessage());
			assertEquals("unexpected output from pre-commit hook"
					out.toString());
		} catch (Throwable e) {
			fail("unexpected exception thrown by pre-commit hook: " + e);
		}
	}

