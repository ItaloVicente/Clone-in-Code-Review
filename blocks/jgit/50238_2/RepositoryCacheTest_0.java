
	@Test
	public void testGetRegisteredWhenEmpty() {
		assertThat(RepositoryCache.getRegistered().size()
	}

	@Test
	public void testGetRegistered() {
		RepositoryCache.register(db);

		assertThat(RepositoryCache.getRegistered()
				hasItem(FileKey.exact(db.getDirectory()
		assertThat(RepositoryCache.getRegistered().size()
	}

	@Test
	public void testUnregister() {
		RepositoryCache.register(db);
		RepositoryCache
				.unregister(FileKey.exact(db.getDirectory()

		assertThat(RepositoryCache.getRegistered().size()
	}

