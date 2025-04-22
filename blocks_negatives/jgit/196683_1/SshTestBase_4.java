	@Test(expected = TransportException.class)
	public void testSshModifiedHostKeyWithProviderDeny() throws Exception {
		File copiedHosts = new File(knownHosts.getParentFile(),
				"copiedKnownHosts");
		assertTrue("Failed to rename known_hosts",
				knownHosts.renameTo(copiedHosts));
		createKnownHostsFile(knownHosts, "localhost", testPort, publicKey1);
		TestCredentialsProvider provider = new TestCredentialsProvider();
		try {
