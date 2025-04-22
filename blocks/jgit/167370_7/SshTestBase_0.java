		assertThrows(TransportException.class
						+ "/doesntmatter"
	}

	@Test
	public void testSingleCommand() throws Exception {
		installConfig("IdentityFile " + privateKey1.getAbsolutePath());
		String command = SshTestGitServer.ECHO_COMMAND + " 2 without timeout";
		String reply = SshSupport.runSshCommand(
				null
		assertEquals(command
	}

	@Test
	public void testSingleCommandWithTimeout() throws Exception {
		installConfig("IdentityFile " + privateKey1.getAbsolutePath());
		String command = SshTestGitServer.ECHO_COMMAND
				+ " 1 expecting no timeout";
		String reply = SshSupport.runSshCommand(
				null
		assertEquals(command
	}

	@Test
	public void testSingleCommandWithTimeoutExpired() throws Exception {
		installConfig("IdentityFile " + privateKey1.getAbsolutePath());
		String command = SshTestGitServer.ECHO_COMMAND + " 2 EXPECTING TIMEOUT";

		CommandFailedException e = assertThrows(CommandFailedException.class
				() -> SshSupport.runSshCommand(new URIish(
						FS.DETECTED
		assertTrue(e.getMessage().contains(command));
		assertTrue(e.getMessage().contains("time"));
