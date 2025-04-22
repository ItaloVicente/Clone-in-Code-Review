	@Test(expected = TransportException.class)
	public void testPasswordAuthWrongPassword() throws Exception {
		server.enablePasswordAuthentication();
		TestCredentialsProvider provider = new TestCredentialsProvider(
				"wrongpass");
				"Host git", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"PreferredAuthentications password");
