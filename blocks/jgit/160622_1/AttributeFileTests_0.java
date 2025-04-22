	@Test
	public void testTextAutoCoreEolCoreAutoCrLfInput() throws Exception {
		FileBasedConfig cfg = db.getConfig();
		cfg.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_AUTOCRLF
		cfg.save();
		final String content = "Line1\nLine2\n";
		try (Git git = Git.wrap(db)) {
			writeTrashFile(".gitattributes"
			File dummy = writeTrashFile("dummy.txt"
			git.add().addFilepattern(".").call();
			git.commit().setMessage("Commit with LF").call();
			assertEquals("Unexpected index state"
					"[.gitattributes
							+ "[dummy.txt
							+ ']'
					indexState(CONTENT));
			assertTrue("Should be able to delete " + dummy
			cfg.setString(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_EOL
			cfg.setString(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_AUTOCRLF
			cfg.save();
			git.reset().setMode(ResetType.HARD).call();
			assertTrue("File " + dummy + "should exist"
			String textFile = RawParseUtils.decode(IO.readFully(dummy
			assertEquals("Unexpected text content"
		}
	}

