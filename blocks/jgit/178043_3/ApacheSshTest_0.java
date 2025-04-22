
	@Test
	public void testConnectAuthSshRsa() throws Exception {
		try (SshServer oldServer = createServer(TEST_USER
			oldServer.setSignatureFactoriesNames("ssh-rsa");
			oldServer.start();
			registerServer(oldServer);
			installConfig("Host server"
					"HostName localhost"
					"Port " + oldServer.getPort()
					"User " + TEST_USER
					"IdentityFile " + privateKey1.getAbsolutePath());
			RemoteSession session = getSessionFactory().getSession(
					10000);
			assertNotNull(session);
			session.disconnect();
		}
	}
