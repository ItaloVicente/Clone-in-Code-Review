	@Test(expected = CommandFailedException.class)
	public void testReadPipePosixCommandFailure()
			throws CommandFailedException {
		FS fs = FS.DETECTED.newInstance();
		assumeTrue(fs instanceof FS_POSIX);

		FS.readPipe(fs.userHome(),
				new String[] { "/bin/sh", "-c", "exit 1" },
				SystemReader.getInstance().getDefaultCharset().name());
