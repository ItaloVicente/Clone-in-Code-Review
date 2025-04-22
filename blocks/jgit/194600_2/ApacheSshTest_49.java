
	private void verifyAuthLog(String message
		assertTrue(message.contains(System.lineSeparator()));
		String[] lines = message.split(System.lineSeparator());
		int pubkeyIndex = -1;
		int passwordIndex = -1;
		for (int i = 0; i < lines.length; i++) {
			String line = lines[i];
			if (i == 0) {
				assertTrue(line.contains(first));
			}
			if (line.contains("publickey:")) {
				if (pubkeyIndex < 0) {
					pubkeyIndex = i;
					assertTrue(line.contains("/userkey"));
				}
			} else if (line.contains("password:")) {
				if (passwordIndex < 0) {
					passwordIndex = i;
					assertTrue(line.contains("attempt 1"));
				}
			}
		}
		assertTrue(pubkeyIndex > 0 && passwordIndex > 0);
		assertTrue(pubkeyIndex < passwordIndex);
	}

	@Test
	public void testAuthFailureMessageCancel() throws Exception {
		File userKey = new File(getTemporaryDirectory()
		copyTestResource("id_ed25519"
		File publicKey = new File(getTemporaryDirectory()
		copyTestResource("id_ed25519.pub"
		server.enablePasswordAuthentication();
		TestCredentialsProvider provider = new TestCredentialsProvider(
				"wrongpass");
		TransportException e = assertThrows(TransportException.class
						provider
						"Host git"
						"HostName localhost"
						"Port " + testPort
						"User " + TEST_USER
						"IdentityFile " + userKey.getAbsolutePath()
						"PreferredAuthentications publickey
		verifyAuthLog(e.getMessage()
	}

	@Test
	public void testAuthFailureMessage() throws Exception {
		File userKey = new File(getTemporaryDirectory()
		copyTestResource("id_ed25519"
		File publicKey = new File(getTemporaryDirectory()
		copyTestResource("id_ed25519.pub"
		server.enablePasswordAuthentication();
		TestCredentialsProvider provider = new TestCredentialsProvider(
				"wrongpass"
		TransportException e = assertThrows(TransportException.class
						provider
						"Host git"
						"HostName localhost"
						"Port " + testPort
						"User " + TEST_USER
						"IdentityFile " + userKey.getAbsolutePath()
						"PreferredAuthentications publickey
		verifyAuthLog(e.getMessage()
	}

