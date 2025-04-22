	@Test(expected = TransportException.class)
	public void testKeyboardInteractiveAuthWrongPassword() throws Exception {
		server.enableKeyboardInteractiveAuthentication();
		TestCredentialsProvider provider = new TestCredentialsProvider(
				"wrongpass");
				"Host git", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"PreferredAuthentications keyboard-interactive");
