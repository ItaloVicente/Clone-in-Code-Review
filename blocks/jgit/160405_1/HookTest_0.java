	@Test
	public void testHookPathWithBlank() throws Exception {
		assumeSupportedPlatform();

		File file = writeHookFile("../../a directory/" + PreCommitHook.NAME
				"#!/bin/sh\necho \"test $1 $2\"\nread INPUT\necho $INPUT\n"
						+ "echo $GIT_DIR\necho $GIT_WORK_TREE\necho 1>&2 \"stderr\"");
		StoredConfig cfg = db.getConfig();
		cfg.load();
		cfg.setString(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_HOOKS_PATH
				file.getParentFile().getAbsolutePath());
		cfg.save();
		try (ByteArrayOutputStream out = new ByteArrayOutputStream();
				ByteArrayOutputStream err = new ByteArrayOutputStream()) {
			ProcessResult res = FS.DETECTED.runHookIfPresent(db
					PreCommitHook.NAME
					new PrintStream(out)

			assertEquals("unexpected hook output"
					"test arg1 arg2\nstdin\n"
							+ db.getDirectory().getAbsolutePath() + '\n'
							+ db.getWorkTree().getAbsolutePath() + '\n'
					out.toString("UTF-8"));
			assertEquals("unexpected output on stderr stream"
					err.toString("UTF-8"));
			assertEquals("unexpected exit code"
			assertEquals("unexpected process status"
					res.getStatus());
		}
	}

