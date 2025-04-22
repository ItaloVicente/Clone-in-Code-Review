		assertThrows(TransportException.class
						+ "/doesntmatter"
	}

	@Test
	public void testSingleCommand() throws Exception {
		installConfig("IdentityFile " + privateKey1.getAbsolutePath());
		String command = SshTestGitServer.ECHO_COMMAND + " 2 something";
		String reply = SshSupport.runSshCommand(
				null
		assertEquals(command
	}

	@Test
	public void testSingleCommandWithTimeout() throws Exception {
		installConfig("IdentityFile " + privateKey1.getAbsolutePath());
		String command = SshTestGitServer.ECHO_COMMAND + " 1 something";
		String reply = SshSupport.runSshCommand(
				null
		assertEquals(command
	}

	@Test
	public void testSingleCommandWithTimeoutExpired() throws Exception {
		installConfig("IdentityFile " + privateKey1.getAbsolutePath());
		String command = SshTestGitServer.ECHO_COMMAND + " 2 something";

		CommandFailedException e = assertThrows(CommandFailedException.class
				() -> SshSupport.runSshCommand(new URIish(
						FS.DETECTED
		assertTrue(e.getMessage().contains(command));
		assertTrue(e.getMessage().contains("time"));
