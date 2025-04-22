		File cloned = new File(getTemporaryDirectory(), "cloned");
		String keyFileName = keyName + "_key";
		File privateKey = new File(sshDir, keyFileName);
		copyTestResource(keyName, privateKey);
		File publicKey = new File(sshDir, keyFileName + ".pub");
		copyTestResource(keyName + ".pub", publicKey);
		server.setTestUserPublicKey(publicKey.toPath());
		TestCredentialsProvider provider = new TestCredentialsProvider(
				"testpass");
		pushTo(provider,
						cloned, provider, //
						"Host localhost", //
						"HostName localhost", //
						"Port " + testPort, //
						"User " + TEST_USER, //
						"IdentityFile " + privateKey.getAbsolutePath()));
		int expectedCalls = keyName.endsWith("testpass") ? 1 : 0;
		assertEquals("Unexpected calls to CredentialsProvider", expectedCalls,
				provider.getLog().size());
		cloned = new File(getTemporaryDirectory(), "cloned2");
		pushTo(null,
						cloned, null, //
						"Host localhost", //
						"HostName localhost", //
						"Port " + testPort, //
						"User " + TEST_USER, //
						"IdentityFile " + privateKey.getAbsolutePath()));
