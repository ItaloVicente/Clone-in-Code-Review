	@Test
	void testSshWithoutKnownHostsDeny() throws Exception {
		assertThrows(TransportException.class
			File copiedHosts = new File(knownHosts.getParentFile()
					"copiedKnownHosts");
			assertTrue(knownHosts.renameTo(copiedHosts)
					"Failed to rename known_hosts");
					"Host localhost"
					"HostName localhost"
					"Port " + testPort
					"User " + TEST_USER
					"StrictHostKeyChecking yes"
					"IdentityFile " + privateKey1.getAbsolutePath());
		});
