	@Test(expected = TransportException.class)
	public void testSshEncryptedUsedKeyWrongPassword() throws Exception {
		File encryptedKey = new File(sshDir
		copyTestResource("id_dsa_testpass"
		File encryptedPublicKey = new File(sshDir
		copyTestResource("id_dsa_testpass.pub"
		server.setTestUserPublicKey(encryptedPublicKey.toPath());
		TestCredentialsProvider provider = new TestCredentialsProvider(
				"wrongpass");
				defaultCloneDir
				"Host localhost"
				"HostName localhost"
				"Port " + testPort
				"User " + TEST_USER
				"NumberOfPasswordPrompts 1"
				"IdentityFile " + encryptedKey.getAbsolutePath());
	}

	@Test
	public void testSshEncryptedUsedKeySeveralPassword() throws Exception {
		File encryptedKey = new File(sshDir
		copyTestResource("id_dsa_testpass"
		File encryptedPublicKey = new File(sshDir
		copyTestResource("id_dsa_testpass.pub"
		server.setTestUserPublicKey(encryptedPublicKey.toPath());
		TestCredentialsProvider provider = new TestCredentialsProvider(
				"wrongpass"
				defaultCloneDir
				"Host localhost"
				"HostName localhost"
				"Port " + testPort
				"User " + TEST_USER
				"IdentityFile " + encryptedKey.getAbsolutePath());
		assertEquals("CredentialsProvider should have been called 3 times"
				provider.getLog().size());
	}

