	@Test
	public void testAutoCRLFInput() throws Exception {
		Git git = new Git(db);
		FileBasedConfig config = db.getConfig();

		config.setEnum(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_AUTOCRLF
		config.save();

		writeTrashFile("crlf.txt"
		writeTrashFile("lf.txt"
		git.add().addFilepattern("crlf.txt").addFilepattern("lf.txt").call();
		git.commit().setMessage("Add files").call();

		config.setEnum(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_AUTOCRLF
		config.save();

		writeTrashFile("lf.txt"

		FileTreeIterator iterator = new FileTreeIterator(db);
		IndexDiff diff = new IndexDiff(db
		diff.diff();

		assertEquals(Collections.singleton("lf.txt")
	}

