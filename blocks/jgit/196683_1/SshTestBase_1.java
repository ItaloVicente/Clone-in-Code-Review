				"IdentityFile " + encryptedKey.getAbsolutePath()));
		assertEquals(1
				"CredentialsProvider should have been called once");
	}

	@Test
	void testSshEncryptedUsedKeyWrongPassword() throws Exception {
		assertThrows(TransportException.class
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
		});
