	@Test(expected = TransportException.class)
	public void testKeyboardInteractiveAuthCorrectPasswordTooLate()
			throws Exception {
		server.enableKeyboardInteractiveAuthentication();
		TestCredentialsProvider provider = new TestCredentialsProvider(
				"wrongpass", "wrongpass", "wrongpass",
				TEST_USER.toUpperCase(Locale.ROOT));
				"Host git", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"PreferredAuthentications keyboard-interactive");
