	@Test(expected = TransportException.class)
	public void testKeyboardInteractiveAuthNoPassword() throws Exception {
		server.enableKeyboardInteractiveAuthentication();
		TestCredentialsProvider provider = new TestCredentialsProvider();
				"Host git", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"PreferredAuthentications keyboard-interactive");
