
	public void testSetFileMode() {
		final DirCacheEntry e = new DirCacheEntry("a");

		assertEquals(0

		e.setFileMode(FileMode.REGULAR_FILE);
		assertSame(FileMode.REGULAR_FILE
		assertEquals(FileMode.REGULAR_FILE.getBits()

		e.setFileMode(FileMode.EXECUTABLE_FILE);
		assertSame(FileMode.EXECUTABLE_FILE
		assertEquals(FileMode.EXECUTABLE_FILE.getBits()

		e.setFileMode(FileMode.SYMLINK);
		assertSame(FileMode.SYMLINK
		assertEquals(FileMode.SYMLINK.getBits()

		e.setFileMode(FileMode.GITLINK);
		assertSame(FileMode.GITLINK
		assertEquals(FileMode.GITLINK.getBits()

		try {
			e.setFileMode(FileMode.MISSING);
			fail("incorrectly accepted FileMode.MISSING");
		} catch (IllegalArgumentException err) {
			assertEquals("Invalid mode 0 for path a"
		}

		try {
			e.setFileMode(FileMode.TREE);
			fail("incorrectly accepted FileMode.TREE");
		} catch (IllegalArgumentException err) {
			assertEquals("Invalid mode 40000 for path a"
		}
	}
