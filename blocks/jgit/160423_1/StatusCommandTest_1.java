
	@Test
	public void testExecutableWithNonNormalizedIndex() throws Exception {
		assumeTrue(FS.DETECTED.supportsExecute());
		try (Git git = new Git(db)) {
			FileBasedConfig config = db.getConfig();
			config.setString("core"
			config.save();
			File testFile = writeTrashFile("file.txt"
			FS.DETECTED.setExecute(testFile
			git.add().addFilepattern("file.txt").call();
			git.commit().setMessage("Initial").call();
			assertEquals(
					"[file.txt
					indexState(CONTENT));
			config.setString("core"
			config.save();
			Status status = git.status().call();
			assertTrue("Expected no differences"
		}
	}
