		assertEquals(1
		assertTrue(RepositoryCache.isCached(d2));
	}

	@Test
	public void testRepositoryUnregisteringWhenExpired() throws Exception {
		Repository repoA = createBareRepository();
		Repository repoB = createBareRepository();
		Repository repoC = createBareRepository();
		RepositoryCache.register(repoA);
		RepositoryCache.register(repoB);
		RepositoryCache.register(repoC);

		assertEquals(3
		assertTrue(RepositoryCache.isCached(repoA));
		assertTrue(RepositoryCache.isCached(repoB));
		assertTrue(RepositoryCache.isCached(repoC));

		repoA.close();
		repoA.closedAt.set(System.currentTimeMillis() - 25000);
		repoB.close();

		assertEquals(3
		assertTrue(RepositoryCache.isCached(repoA));
		assertTrue(RepositoryCache.isCached(repoB));
		assertTrue(RepositoryCache.isCached(repoC));

		RepositoryCache.clearExpired();

		assertEquals(2
		assertFalse(RepositoryCache.isCached(repoA));
		assertTrue(RepositoryCache.isCached(repoB));
		assertTrue(RepositoryCache.isCached(repoC));
	}

	@Test
	public void testReconfigure() throws InterruptedException {
		RepositoryCache.register(db);
		assertTrue(RepositoryCache.isCached(db));
		db.close();
		assertTrue(RepositoryCache.isCached(db));

		RepositoryCacheConfig config = new RepositoryCacheConfig();
		config.setExpireAfter(1);
		config.setCleanupDelay(1);
		config.install();

		int t = 1;
		for (int i = 0; i <= 10; i++) {
			Thread.sleep(t);
			if (!RepositoryCache.isCached(db)) {
				return;
			}
			t = t * 2;
		}
		fail("Repository should have been evicted from cache");
