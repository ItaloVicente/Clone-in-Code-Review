
	@Test
	public void testCreateSymlink() throws IOException {
		FS fs = FS.DETECTED;
		try {
			fs.createSymLink(new File(trash
		} catch (IOException e) {
			if (fs.supportsSymlinks())
				fail("FS claims to support symlinks but attempt to create symlink failed");
			return;
		}
		assertTrue(fs.supportsSymlinks());
		String target = fs.readSymLink(new File(trash
		assertEquals("y"
	}
