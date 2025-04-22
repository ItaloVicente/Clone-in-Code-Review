	@Test
	public void testRepeatedLookups() throws Exception {
		config("Host orcz\n" + "\tConnectionAttempts 5\n");
		final Host h1 = osc.lookup("orcz");
		final Host h2 = osc.lookup("orcz");
		assertNotNull(h1);
		assertSame(h1, h2);
		assertEquals(5, h1.getConnectionAttempts());
		assertEquals(h1.getConnectionAttempts(), h2.getConnectionAttempts());
		final ConfigRepository.Config c = osc.getConfig("orcz");
		assertNotNull(c);
		assertSame(c, h1.getConfig());
		assertSame(c, h2.getConfig());
	}

