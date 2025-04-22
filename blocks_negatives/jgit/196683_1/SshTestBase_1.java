		pushTo(provider,
						defaultCloneDir, provider, //
						"Host localhost", //
						"HostName localhost", //
						"Port " + testPort, //
						"User " + TEST_USER, //
						"IdentityFile " + encryptedKey.getAbsolutePath()));
		assertEquals("CredentialsProvider should have been called once", 1,
				provider.getLog().size());
	}

	@Test(expected = TransportException.class)
	public void testSshEncryptedUsedKeyWrongPassword() throws Exception {
		File encryptedKey = new File(sshDir, "id_dsa_test_key");
		copyTestResource("id_dsa_testpass", encryptedKey);
		File encryptedPublicKey = new File(sshDir, "id_dsa_test_key.pub");
		copyTestResource("id_dsa_testpass.pub", encryptedPublicKey);
		server.setTestUserPublicKey(encryptedPublicKey.toPath());
		TestCredentialsProvider provider = new TestCredentialsProvider(
				"wrongpass");
