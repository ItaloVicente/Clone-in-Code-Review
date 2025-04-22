	@Test
	public void testCreateSymlink() throws IOException {
		FS fs = FS.DETECTED;
		if (!fs.supportsSymlinks())
			return;
		fs.createSymLink(new File(trash
		String target = fs.readSymLink(new File(trash
		assertEquals("y"
	}
