	@Test
	public void testSshWithUnknownAuthInConfig() throws Exception {
				"Host git"
				"HostName localhost"
				"Port " + testPort
				"User " + TEST_USER
				"IdentityFile " + privateKey1.getAbsolutePath()
				"PreferredAuthentications gssapi-with-mic
	}

	@Test(expected = TransportException.class)
	public void testSshWithNoMatchingAuthInConfig() throws Exception {
				"Host git"
				"HostName localhost"
				"Port " + testPort
				"User " + TEST_USER
				"IdentityFile " + privateKey1.getAbsolutePath()
				"PreferredAuthentications password");
	}

