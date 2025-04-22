
	@Test
	public void testPrefix() throws Exception {
		File a = writeTrashFile("a.txt"
		File b = writeTrashFile("a/a.txt"
		File dir = b.getParentFile();
		git.clean().call();
		assertFalse(a.exists());
		assertTrue(dir.exists());
		assertTrue(b.exists());
	}

	@Test
	public void testPrefixWithDir() throws Exception {
		File a = writeTrashFile("a.txt"
		File b = writeTrashFile("a/a.txt"
		File dir = b.getParentFile();
		git.clean().setCleanDirectories(true).call();
		assertFalse(a.exists());
		assertFalse(dir.exists());
	}
