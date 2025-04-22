	@Test(expected = TransportException.class)
	public void testSshWithoutKnownHosts() throws Exception {
		assertTrue("Could not delete known_hosts", knownHosts.delete());
				"Host localhost", //
				"HostName localhost", //
				"Port " + testPort, //
				"User " + TEST_USER, //
				"IdentityFile " + privateKey1.getAbsolutePath());
