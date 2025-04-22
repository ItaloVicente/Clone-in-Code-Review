	}

	@Test(expected = CommandFailedException.class)
	public void testReadPipePosixCommandStartFailure()
			throws CommandFailedException {
		FS fs = FS.DETECTED.newInstance();
		assumeTrue(fs instanceof FS_POSIX);

		FS.readPipe(fs.userHome()
				  new String[] { "this-command-does-not-exist" }
				  Charset.defaultCharset().name());
