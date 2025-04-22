
	@Test
	public void rmCachedMultiple() throws Exception {
		File a = writeTrashFile("a"
		File b = writeTrashFile("b"
		git.add().addFilepattern("a").addFilepattern("b").call();

		String[] result = execute("git rm --cached a b");
		assertArrayEquals(new String[] { "" }
		DirCache cache = db.readDirCache();
		assertNull(cache.getEntry("a"));
		assertNull(cache.getEntry("b"));
		assertTrue(a.exists());
		assertTrue(b.exists());
	}
