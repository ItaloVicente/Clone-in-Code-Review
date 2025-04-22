
	@Test
	public void testResolveFile() throws IOException {
		String homeDir = System.getProperty("user.home");
		File tempFileInHomeDirectory = File.createTempFile("testResolveFile"
				".tmp"
		tempFileInHomeDirectory.deleteOnExit();

		assertNotNull(FileUtils.resolveFile(FS.DETECTED
				tempFileInHomeDirectory.getPath()));

		String pathWithTilde = tempFileInHomeDirectory.getPath()
				.replace(homeDir

		File resolvedFile = FileUtils.resolveFile(FS.DETECTED
		assertNotNull(resolvedFile);
		assertEquals("tilde should be resolved"
				tempFileInHomeDirectory.getPath()
	}
