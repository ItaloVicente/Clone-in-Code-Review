	@Test
	void testPasswordAuthWrongPassword() throws Exception {
		assertThrows(TransportException.class
			server.enablePasswordAuthentication();
			TestCredentialsProvider provider = new TestCredentialsProvider(
					"wrongpass");
					"Host git"
					"HostName localhost"
					"Port " + testPort
					"User " + TEST_USER
					"PreferredAuthentications password");
		});
