	@Test
	void testKeyboardInteractiveAuthNoPassword() throws Exception {
		assertThrows(TransportException.class
			server.enableKeyboardInteractiveAuthentication();
			TestCredentialsProvider provider = new TestCredentialsProvider();
					"Host git"
					"HostName localhost"
					"Port " + testPort
					"User " + TEST_USER
					"PreferredAuthentications keyboard-interactive");
		});
