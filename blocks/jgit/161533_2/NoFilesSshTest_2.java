		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		generator.initialize(2048);
		testUserKey = generator.generateKeyPair();
		KeyPair hostKey = generator.generateKeyPair();
		server.addHostKey(hostKey
		testServerKey = hostKey.getPublic();
