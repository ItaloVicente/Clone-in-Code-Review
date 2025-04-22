	@Test(expected = CommandFailedException.class)
	public void testReadPipePosixCommandFailure()
			throws CommandFailedException {
		FS fs = FS.DETECTED.newInstance();
		assumeTrue(fs instanceof FS_POSIX);

		String r = FS.readPipe(fs.userHome()
				new String[] { "bash"
				Charset.defaultCharset().name());
		System.out.println(r);
	}
