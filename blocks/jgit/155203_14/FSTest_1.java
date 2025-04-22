
	@Test
	public void testResolveHomeAware() throws IOException {
		String homeDir = System.getProperty("user.home");
		File tempFileInHomeDirectory = File.createTempFile("testResolveFile"
				".tmp"
		tempFileInHomeDirectory.deleteOnExit();
		assertNotNull(FS.DETECTED.resolve(tempFileInHomeDirectory.getPath()));

		String pathWithTilde = tempFileInHomeDirectory.getPath()
				.replace(homeDir
		File resolvedFile = FS.DETECTED.resolve(pathWithTilde);
		assertNotNull(resolvedFile);
		assertEquals("tilde should be resolved"
				tempFileInHomeDirectory.getPath()
	}
