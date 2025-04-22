	@Test
	void testReadPipeCommandStartFailure() {
		assertThrows(CommandFailedException.class
			FS fs = FS.DETECTED.newInstance();

			FS.readPipe(fs.userHome()
					new String[]{"this-command-does-not-exist"}
					SystemReader.getInstance().getDefaultCharset().name());
		});
