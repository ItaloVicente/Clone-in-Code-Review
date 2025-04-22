	@Test
	public void testUnicodeFilePath() throws IOException {
		Assume.assumeTrue(FS.DETECTED.supportsSymlinks());
		FS fs = FS.DETECTED;
		File link = new File(trash
		File target = new File(trash

		try {
			link.toPath();
			target.toPath();
		} catch (InvalidPathException e) {
			assumeNoException(e);
		}

		fs.createSymLink(link
		assertTrue(fs.exists(link));
		assertEquals("å"
	}

