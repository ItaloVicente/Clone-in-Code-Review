	@Test(expected = TransportException.class)
	public void testSshCloneWithoutKnownHosts() throws Exception {
		assertTrue("Could not delete known_hosts", knownHosts.delete());
				"Host localhost", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"IdentityFile " + privateKey1.getAbsolutePath());
	}

	@Test
	public void testSshCloneWithoutKnownHostsWithProvider() throws Exception {
		File copiedHosts = new File(knownHosts.getParentFile(),
				"copiedKnownHosts");
		assertTrue("Failed to rename known_hosts",
				knownHosts.renameTo(copiedHosts));
		TestCredentialsProvider provider = new TestCredentialsProvider();
				"Host localhost", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"IdentityFile " + privateKey1.getAbsolutePath());
		Map<URIish, List<CredentialItem>> messages = provider.getLog();
		assertFalse("Expected user iteraction", messages.isEmpty());
	}

	@Test
	public void testSftpCloneWithConfig() throws Exception {
				"Host localhost", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"IdentityFile " + privateKey1.getAbsolutePath());
	}

	@Test(expected = TransportException.class)
	public void testSshCloneWithConfigWrongKey() throws Exception {
				"Host localhost", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"IdentityFile " + privateKey2.getAbsolutePath());
	}

	@Test
	public void testSshCloneWithWrongUserNameInConfig() throws Exception {
		cloneWith(
						+ "/doesntmatter",
				null, //
				"Host localhost", //
				"HostName localhost", //
				"User sombody_else", //
				"IdentityFile " + privateKey1.getAbsolutePath());
	}

	@Test
	public void testSshCloneWithWrongPortInConfig() throws Exception {
		cloneWith(
						+ "/doesntmatter",
				null, //
				"Host localhost", //
				"HostName localhost", //
				"Port 22", //
				"User " + TEST_USER, //
				"IdentityFile " + privateKey1.getAbsolutePath());
	}

	@Test
	public void testSshCloneWithAliasInConfig() throws Exception {
				"Host git", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"IdentityFile " + privateKey1.getAbsolutePath(), "", //
				"Host localhost", //
				"HostName localhost", //
				"Port 22", //
				"User someone_else", //
				"IdentityFile " + privateKey2.getAbsolutePath());
	}

	@Test
	public void testSshCloneWithUnknownCiphersInConfig() throws Exception {
				"Host git", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"IdentityFile " + privateKey1.getAbsolutePath(), //
				"Ciphers chacha20-poly1305@openssh.com,aes256-gcm@openssh.com,aes128-gcm@openssh.com,aes256-ctr,aes192-ctr,aes128-ctr");
	}

	@Test
	public void testSshCloneWithUnknownHostKeyAlgorithmsInConfig()
			throws Exception {
				"Host git", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"IdentityFile " + privateKey1.getAbsolutePath(), //
				"HostKeyAlgorithms foobar,ssh-rsa,ssh-dss");
	}

	@Test
	public void testSshCloneWithUnknownKexAlgorithmsInConfig()
			throws Exception {
				"Host git", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"IdentityFile " + privateKey1.getAbsolutePath(), //
				"KexAlgorithms foobar,diffie-hellman-group14-sha1,ecdh-sha2-nistp256,ecdh-sha2-nistp384,ecdh-sha2-nistp521");
	}

	@Test
	public void testSshCloneWithMinimalHostKeyAlgorithmsInConfig()
			throws Exception {
				"Host git", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"IdentityFile " + privateKey1.getAbsolutePath(), //
				"HostKeyAlgorithms ssh-rsa,ssh-dss");
	}

	private void cloneWith(String uri, CredentialsProvider provider,
