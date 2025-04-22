		TestCredentialsProvider provider = new TestCredentialsProvider(
				"testpass");
				"Host localhost", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"IdentityFile " + privateKey1.getAbsolutePath());
		assertEquals("CredentialsProvider should not have been called", 0,
				provider.getLog().size());
