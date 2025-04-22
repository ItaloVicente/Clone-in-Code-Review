	public void testSymlink() throws IOException {
		if (skipTest() || !isPosix())
			return;

		final File path = new File(root
		String dst = "dst";

		access.symlink(path
		FileInfo info = access.lstat(path);
		assertTrue(FileMode.SYMLINK.equals(info.mode()));
		assertEquals(dst
		path.delete();

		dst = longString(510);
		access.symlink(path
		info = access.lstat(path);
		assertTrue(FileMode.SYMLINK.equals(info.mode()));
		assertEquals(dst

		dst = "a";
		try {
			access.symlink(path
			fail("symlink replaced an existing link");
		} catch (FileExistsException exists) {
			assertEquals(path.getPath()
		}
	}

