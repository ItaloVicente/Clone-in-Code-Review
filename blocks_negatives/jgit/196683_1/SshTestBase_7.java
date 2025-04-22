	@Test(expected = TransportException.class)
	public void testSshWithNoMatchingAuthInConfig() throws Exception {
				"Host git", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"IdentityFile " + privateKey1.getAbsolutePath(), //
				"PreferredAuthentications password");
