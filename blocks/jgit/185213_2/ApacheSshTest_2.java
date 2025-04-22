
	@Test
	public void testConnectOnlyRsaSha1() throws Exception {
		try (SshServer oldServer = createServer(TEST_USER
			oldServer.setSignatureFactoriesNames("ssh-rsa");
			List<DHFactory> sha1Factories = BuiltinDHFactories
					.parseDHFactoriesList(
							"diffie-hellman-group1-sha1
					.getParsedFactories();
			assertEquals(2
			List<KeyExchangeFactory> kexFactories = NamedFactory
					.setUpTransformedFactories(true
							ServerBuilder.DH2KEX);
			oldServer.setKeyExchangeFactories(kexFactories);
			oldServer.start();
			registerServer(oldServer);
			installConfig("Host server"
					"HostName localhost"
					"Port " + oldServer.getPort()
					"User " + TEST_USER
					"IdentityFile " + privateKey1.getAbsolutePath()
					"KexAlgorithms +diffie-hellman-group1-sha1");
			RemoteSession session = getSessionFactory().getSession(
					10000);
			assertNotNull(session);
			session.disconnect();
		}
	}
