	@Test
	void testSshWithConfigWrongKey() throws Exception {
		assertThrows(TransportException.class
					"Host localhost"
					"HostName localhost"
					"Port " + testPort
					"User " + TEST_USER
					"IdentityFile " + privateKey2.getAbsolutePath());
		});
