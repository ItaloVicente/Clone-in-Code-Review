	@Test
	public void testPasswordAuth() throws Exception {
		server.enablePasswordAuthentication();
		TestCredentialsProvider provider = new TestCredentialsProvider(
				TEST_USER.toUpperCase(Locale.ROOT));
				"Host git"
				"HostName localhost"
				"Port " + testPort
				"User " + TEST_USER
				"PreferredAuthentications password");
	}

	@Test
	public void testPasswordAuthSeveralTimes() throws Exception {
		server.enablePasswordAuthentication();
		TestCredentialsProvider provider = new TestCredentialsProvider(
				"wrongpass"
				"Host git"
				"HostName localhost"
				"Port " + testPort
				"User " + TEST_USER
				"PreferredAuthentications password");
	}

	@Test(expected = TransportException.class)
	public void testPasswordAuthWrongPassword() throws Exception {
		server.enablePasswordAuthentication();
		TestCredentialsProvider provider = new TestCredentialsProvider(
				"wrongpass");
				"Host git"
				"HostName localhost"
				"Port " + testPort
				"User " + TEST_USER
				"PreferredAuthentications password");
	}

	@Test(expected = TransportException.class)
	public void testPasswordAuthNoPassword() throws Exception {
		server.enablePasswordAuthentication();
		TestCredentialsProvider provider = new TestCredentialsProvider();
				"Host git"
				"HostName localhost"
				"Port " + testPort
				"User " + TEST_USER
				"PreferredAuthentications password");
	}

	@Test(expected = TransportException.class)
	public void testPasswordAuthCorrectPasswordTooLate() throws Exception {
		server.enablePasswordAuthentication();
		TestCredentialsProvider provider = new TestCredentialsProvider(
				"wrongpass"
				TEST_USER.toUpperCase(Locale.ROOT));
				"Host git"
				"HostName localhost"
				"Port " + testPort
				"User " + TEST_USER
				"PreferredAuthentications password");
	}

	@Test
	public void testKeyboardInteractiveAuth() throws Exception {
		server.enableKeyboardInteractiveAuthentication();
		TestCredentialsProvider provider = new TestCredentialsProvider(
				TEST_USER.toUpperCase(Locale.ROOT));
				"Host git"
				"HostName localhost"
				"Port " + testPort
				"User " + TEST_USER
				"PreferredAuthentications keyboard-interactive");
	}

	@Test
	public void testKeyboardInteractiveAuthSeveralTimes() throws Exception {
		server.enableKeyboardInteractiveAuthentication();
		TestCredentialsProvider provider = new TestCredentialsProvider(
				"wrongpass"
				"Host git"
				"HostName localhost"
				"Port " + testPort
				"User " + TEST_USER
				"PreferredAuthentications keyboard-interactive");
	}

	@Test(expected = TransportException.class)
	public void testKeyboardInteractiveAuthWrongPassword() throws Exception {
		server.enableKeyboardInteractiveAuthentication();
		TestCredentialsProvider provider = new TestCredentialsProvider(
				"wrongpass");
				"Host git"
				"HostName localhost"
				"Port " + testPort
				"User " + TEST_USER
				"PreferredAuthentications keyboard-interactive");
	}

	@Test(expected = TransportException.class)
	public void testKeyboardInteractiveAuthNoPassword() throws Exception {
		server.enableKeyboardInteractiveAuthentication();
		TestCredentialsProvider provider = new TestCredentialsProvider();
				"Host git"
				"HostName localhost"
				"Port " + testPort
				"User " + TEST_USER
				"PreferredAuthentications keyboard-interactive");
	}

	@Test(expected = TransportException.class)
	public void testKeyboardInteractiveAuthCorrectPasswordTooLate()
			throws Exception {
		server.enableKeyboardInteractiveAuthentication();
		TestCredentialsProvider provider = new TestCredentialsProvider(
				"wrongpass"
				TEST_USER.toUpperCase(Locale.ROOT));
				"Host git"
				"HostName localhost"
				"Port " + testPort
				"User " + TEST_USER
				"PreferredAuthentications keyboard-interactive");
	}

