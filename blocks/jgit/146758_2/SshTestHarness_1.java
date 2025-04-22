	protected void runKeyTest(String keyName) throws Exception {
		File cloned = new File(getTemporaryDirectory()
		String keyFileName = keyName + "_key";
		File privateKey = new File(sshDir
		copyTestResource(keyName
		File publicKey = new File(sshDir
		copyTestResource(keyName + ".pub"
		server.setTestUserPublicKey(publicKey.toPath());
		TestCredentialsProvider provider = new TestCredentialsProvider(
				"testpass");
		pushTo(provider
				cloned
				"Host localhost"
				"HostName localhost"
				"Port " + testPort
				"User " + TEST_USER
				"IdentityFile " + privateKey.getAbsolutePath()));
		int expectedCalls = keyName.contains("testpass") ? 1 : 0;
		assertEquals("Unexpected calls to CredentialsProvider"
				provider.getLog().size());
		cloned = new File(getTemporaryDirectory()
		pushTo(null
				cloned
				"Host localhost"
				"HostName localhost"
				"Port " + testPort
				"User " + TEST_USER
				"IdentityFile " + privateKey.getAbsolutePath()));
	}

