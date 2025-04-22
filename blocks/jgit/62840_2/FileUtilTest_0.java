	@Test
	public void testCreateSymlinkOverrideExisting() throws IOException {
		FS fs = FS.DETECTED;
		File file = new File(trash
		try {
			fs.createSymLink(file
		} catch (IOException e) {
			if (fs.supportsSymlinks()) {
				fail("FS claims to support symlinks but attempt to create symlink failed");
			} else {
				Assume.assumeTrue(false);
			}
			return;
		}
		assertTrue(fs.supportsSymlinks());
		String target = fs.readSymLink(file);
		assertEquals("y"
		fs.createSymLink(file
		target = fs.readSymLink(file);
		assertEquals("z"
	}

