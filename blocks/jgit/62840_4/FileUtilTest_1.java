	@Test
	public void testCreateSymlinkOverrideExisting() throws IOException {
		FS fs = FS.DETECTED;
		Assume.assumeTrue(fs.supportsSymlinks());
		File file = new File(trash
		fs.createSymLink(file
		assertTrue(fs.supportsSymlinks());
		String target = fs.readSymLink(file);
		assertEquals("y"
		fs.createSymLink(file
		target = fs.readSymLink(file);
		assertEquals("z"
	}

