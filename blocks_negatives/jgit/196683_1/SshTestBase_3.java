	@Test(expected = TransportException.class)
	public void testSshWithoutKnownHostsDeny() throws Exception {
		File copiedHosts = new File(knownHosts.getParentFile(),
				"copiedKnownHosts");
		assertTrue("Failed to rename known_hosts",
				knownHosts.renameTo(copiedHosts));
				"Host localhost", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"StrictHostKeyChecking yes", //
				"IdentityFile " + privateKey1.getAbsolutePath());
	}

	@Test(expected = TransportException.class)
	public void testSshModifiedHostKeyDeny()
			throws Exception {
		File copiedHosts = new File(knownHosts.getParentFile(),
				"copiedKnownHosts");
		assertTrue("Failed to rename known_hosts",
				knownHosts.renameTo(copiedHosts));
		createKnownHostsFile(knownHosts, "localhost", testPort, publicKey1);
				"Host localhost", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"StrictHostKeyChecking yes", //
				"IdentityFile " + privateKey1.getAbsolutePath());
