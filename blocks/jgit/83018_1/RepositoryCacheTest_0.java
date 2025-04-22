	@Test
	public void testRepositoryUnregisteringWhenExpiredAndUsageCountNegative()
			throws Exception {
		Repository repoA = createBareRepository();
		RepositoryCache.register(repoA);

		assertEquals(1
		assertTrue(RepositoryCache.isCached(repoA));

		repoA.close();
		repoA.close();
		repoA.closedAt.set(System.currentTimeMillis() - 65 * 60 * 1000);

		RepositoryCache.clearExpired();

		assertEquals(0
	}

