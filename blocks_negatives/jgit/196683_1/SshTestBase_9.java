	@Test(expected = TransportException.class)
	public void testPasswordAuthNoPassword() throws Exception {
		server.enablePasswordAuthentication();
		TestCredentialsProvider provider = new TestCredentialsProvider();
				"Host git", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"PreferredAuthentications password");
