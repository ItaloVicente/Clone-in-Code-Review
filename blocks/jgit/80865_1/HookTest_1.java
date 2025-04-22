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

