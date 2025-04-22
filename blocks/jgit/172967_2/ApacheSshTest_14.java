		TransportException e = assertThrows(TransportException.class
				() -> cloneWith(
								+ "/doesntmatter"
						defaultCloneDir
						"IdentityFile " + privateKey1.getAbsolutePath()));
		assertFalse(e.getMessage().contains("timeout"));
		assertTrue(e.getMessage().contains("65536")
				|| e.getMessage().contains("closed"));
	}

	@Test
	public void testCloneAndFetchWithSessionLimit() throws Exception {
		PropertyResolverUtils.updateProperty(server.getPropertyResolver()
				ServerFactoryManager.MAX_CONCURRENT_SESSIONS
				defaultCloneDir
				"Host localhost"
				"HostName localhost"
				"Port " + testPort
				"User " + TEST_USER
