	@Test
	public void testRunHookHooksPathRelative() throws Exception {
		assumeSupportedPlatform();

		writeHookFile(PreCommitHook.NAME
				"#!/bin/sh\necho \"Wrong hook $1 $2\"\nread INPUT\necho $INPUT\n"
						+ "echo $GIT_DIR\necho $GIT_WORK_TREE\necho 1>&2 \"stderr\"");
		writeHookFile("../../" + PreCommitHook.NAME
				"#!/bin/sh\necho \"test $1 $2\"\nread INPUT\necho $INPUT\n"
						+ "echo $GIT_DIR\necho $GIT_WORK_TREE\necho 1>&2 \"stderr\"");
		StoredConfig cfg = db.getConfig();
		cfg.load();
		cfg.setString(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_HOOKS_PATH
		cfg.save();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayOutputStream err = new ByteArrayOutputStream();
		ProcessResult res = FS.DETECTED.runHookIfPresent(db
				new String[] { "arg1"
				new PrintStream(err)

		assertEquals("unexpected hook output"
				"test arg1 arg2\nstdin\n" + db.getDirectory().getAbsolutePath()
						+ '\n' + db.getWorkTree().getAbsolutePath() + '\n'
				out.toString("UTF-8"));
		assertEquals("unexpected output on stderr stream"
				err.toString("UTF-8"));
		assertEquals("unexpected exit code"
		assertEquals("unexpected process status"
				res.getStatus());

	}

	@Test
	public void testRunHookHooksPathAbsolute() throws Exception {
		assumeSupportedPlatform();

		writeHookFile(PreCommitHook.NAME
				"#!/bin/sh\necho \"Wrong hook $1 $2\"\nread INPUT\necho $INPUT\n"
						+ "echo $GIT_DIR\necho $GIT_WORK_TREE\necho 1>&2 \"stderr\"");
		writeHookFile("../../" + PreCommitHook.NAME
				"#!/bin/sh\necho \"test $1 $2\"\nread INPUT\necho $INPUT\n"
						+ "echo $GIT_DIR\necho $GIT_WORK_TREE\necho 1>&2 \"stderr\"");
		StoredConfig cfg = db.getConfig();
		cfg.load();
		cfg.setString(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_HOOKS_PATH
				db.getWorkTree().getAbsolutePath());
		cfg.save();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayOutputStream err = new ByteArrayOutputStream();
		ProcessResult res = FS.DETECTED.runHookIfPresent(db
				new String[] { "arg1"
				new PrintStream(err)

		assertEquals("unexpected hook output"
				"test arg1 arg2\nstdin\n" + db.getDirectory().getAbsolutePath()
						+ '\n' + db.getWorkTree().getAbsolutePath() + '\n'
				out.toString("UTF-8"));
		assertEquals("unexpected output on stderr stream"
				err.toString("UTF-8"));
		assertEquals("unexpected exit code"
		assertEquals("unexpected process status"
				res.getStatus());

	}

