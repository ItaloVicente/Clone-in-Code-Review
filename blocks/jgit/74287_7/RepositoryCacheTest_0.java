
	@Test
	public void testRepositoryUnregisteringwhenExpired()
			throws InterruptedException {
		RepositoryCache.register(db);
		assertEquals(1
		db.close();
		assertEquals(0
		assertEquals(1
		RepositoryCacheConfig config = new RepositoryCacheConfig();
		config.setExpireAfter(1);
		config.setCleanupDelay(1);
		RepositoryCache.reconfigure(config);
		Thread.sleep(50);
		assertEquals(0
	}
