	@Test
	void testReadPipePosixCommandFailure() {
		assertThrows(CommandFailedException.class
			FS fs = FS.DETECTED.newInstance();
			assumeTrue(fs instanceof FS_POSIX);

			FS.readPipe(fs.userHome()
					new String[]{"/bin/sh"
					SystemReader.getInstance().getDefaultCharset().name());
		});
