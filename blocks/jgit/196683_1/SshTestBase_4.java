	@Test
	void testSshModifiedHostKeyDeny() throws Exception {
		assertThrows(TransportException.class
			File copiedHosts = new File(knownHosts.getParentFile()
					"copiedKnownHosts");
			assertTrue(knownHosts.renameTo(copiedHosts)
					"Failed to rename known_hosts");
			createKnownHostsFile(knownHosts
