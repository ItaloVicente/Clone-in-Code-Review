		assertThrows(TransportException.class
						+ "/doesntmatter"
	}

	@Test
	public void testSingleCommand() throws Exception {
		installConfig("IdentityFile " + privateKey1.getAbsolutePath());
		String command = SshTestGitServer.ECHO_COMMAND + " 1 without timeout";
		long start = System.nanoTime();
		String reply = SshSupport.runSshCommand(
				null
		long elapsed = System.nanoTime() - start;
		assertEquals(command
		command = SshTestGitServer.ECHO_COMMAND + " 1 expecting no timeout";
		int timeout = 10 * ((int) TimeUnit.NANOSECONDS.toSeconds(elapsed) + 1);
		reply = SshSupport.runSshCommand(
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
