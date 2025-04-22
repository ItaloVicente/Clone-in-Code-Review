
	@Test
	public void testEd25519HostKey() throws Exception {
		File newHostKey = new File(getTemporaryDirectory()
		copyTestResource("id_ed25519"
		server.addHostKey(newHostKey.toPath()
		File newHostKeyPub = new File(getTemporaryDirectory()
				"newhostkey.pub");
		copyTestResource("id_ed25519.pub"
		createKnownHostsFile(knownHosts
				"Host git"
				"HostName localhost"
				"Port " + testPort
				"User " + TEST_USER
				"IdentityFile " + privateKey1.getAbsolutePath());
	}

