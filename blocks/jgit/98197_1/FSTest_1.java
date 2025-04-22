	}

	@Test(expected = CommandFailedException.class)
	public void testReadPipeCommandStartFailure()
			throws CommandFailedException {
		FS fs = FS.DETECTED.newInstance();

		FS.readPipe(fs.userHome()
				  new String[] { "this-command-does-not-exist" }
				  Charset.defaultCharset().name());
