	@Test
	public void commitWithAutoCrlfAndNonNormalizedIndex() throws Exception {
		Git git = Git.wrap(db);
		FileBasedConfig config = db.getConfig();
		config.setString("core"
		config.save();
		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		git.commit().setMessage("Initial").call();
		assertEquals("[file.txt
				indexState(CONTENT));
		config.setString("core"
		config.save();
		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		git.commit().setMessage("Second").call();
		assertEquals(
				"[file.txt
				indexState(CONTENT));
	}

