
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

		for (int i = 1; i <= 100; i++) {
			if (!RepositoryCache.isCached(db)) {
				return;
			}
			Thread.sleep(i);
		}
		fail("Repository should have been evicted from cache");
	}
