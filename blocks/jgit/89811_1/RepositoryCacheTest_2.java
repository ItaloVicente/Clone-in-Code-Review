	public void testReconfigure() throws InterruptedException
		Repository repo = createRepository(false
		RepositoryCache.register(repo);
		assertTrue(RepositoryCache.isCached(repo));
		repo.close();
		assertTrue(RepositoryCache.isCached(repo));
