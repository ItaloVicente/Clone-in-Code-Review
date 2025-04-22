	@Test
	void testSshWithNoMatchingAuthInConfig() throws Exception {
		assertThrows(TransportException.class
					"Host git"
					"HostName localhost"
					"Port " + testPort
					"User " + TEST_USER
					"IdentityFile " + privateKey1.getAbsolutePath()
					"PreferredAuthentications password");
		});
