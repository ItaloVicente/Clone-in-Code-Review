	@Test
	public void testResetDefaultMode() throws Exception {
		git = new Git(db);
		writeTrashFile("a.txt"
		git.add().addFilepattern("a.txt").call();
		writeTrashFile("a.txt"
		git.reset().call();

		DirCache cache = db.readDirCache();
		DirCacheEntry aEntry = cache.getEntry("a.txt");
		assertNull(aEntry);
		assertEquals("modified"
	}

