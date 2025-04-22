	@Test
	public void testWrongKeyFirst() throws Exception {
		File userKey = new File(getTemporaryDirectory()
		copyTestResource("id_ed25519"
		File publicKey = new File(getTemporaryDirectory()
		copyTestResource("id_ed25519.pub"
		server.setTestUserPublicKey(publicKey.toPath());
				"Host git"
				"HostName localhost"
				"Port " + testPort
				"User " + TEST_USER
				"IdentityFile " + privateKey1.getAbsolutePath()
				"IdentityFile " + userKey.getAbsolutePath());
	}

