	public void testRepositoryUsageCountWithRegisteredRepository()
			throws IOException {
		Repository repo = createRepository(false
		assertEquals(1
		RepositoryCache.register(repo);
		assertEquals(1
		repo.close();
		assertEquals(0
