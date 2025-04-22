	@Test
	public void testRsaHostKeySecond() throws Exception {
		File newHostKey = new File(getTemporaryDirectory()
		copyTestResource("id_ecdsa_256"
		server.addHostKey(newHostKey.toPath()
				"Host git"
				"HostName localhost"
				"Port " + testPort
				"User " + TEST_USER
				"IdentityFile " + privateKey1.getAbsolutePath());
	}

	@Test
	public void testEcDsaHostKey() throws Exception {
		File newHostKey = new File(getTemporaryDirectory()
		copyTestResource("id_ecdsa_256"
		server.addHostKey(newHostKey.toPath()
		File newHostKeyPub = new File(getTemporaryDirectory()
				"newhostkey.pub");
		copyTestResource("id_ecdsa_256.pub"
		createKnownHostsFile(knownHosts
				"Host git"
				"HostName localhost"
				"Port " + testPort
				"User " + TEST_USER
				"IdentityFile " + privateKey1.getAbsolutePath());
	}

