
	@Test
	public void testAlias_ConnectionAttemptsDefault() throws Exception {
		final Host h = osc.lookup("orcz");
		assertNotNull(h);
		assertEquals(1
	}

	@Test
	public void testAlias_ConnectionAttempts() throws Exception {
		config("Host orcz\n" + "\tConnectionAttempts 5\n");
		final Host h = osc.lookup("orcz");
		assertNotNull(h);
		assertEquals(5
	}

	@Test
	public void testAlias_invalidConnectionAttempts() throws Exception {
		config("Host orcz\n" + "\tConnectionAttempts -1\n");
		final Host h = osc.lookup("orcz");
		assertNotNull(h);
		assertEquals(1
	}

	@Test
	public void testAlias_badConnectionAttempts() throws Exception {
		config("Host orcz\n" + "\tConnectionAttempts xxx\n");
		final Host h = osc.lookup("orcz");
		assertNotNull(h);
		assertEquals(1
	}
