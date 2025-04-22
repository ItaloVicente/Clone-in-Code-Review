
	@Test
	public void testGetRegisteredWhenEmpty() {
		assertThat(RepositoryCache.getRegisteredKeys().size()
	}

	@Test
	public void testGetRegistered() {
		RepositoryCache.register(db);

		assertThat(RepositoryCache.getRegisteredKeys()
				hasItem(FileKey.exact(db.getDirectory()
		assertThat(RepositoryCache.getRegisteredKeys().size()
	}

	@Test
	public void testUnregister() {
		RepositoryCache.register(db);
		RepositoryCache
				.unregister(FileKey.exact(db.getDirectory()

		assertThat(RepositoryCache.getRegisteredKeys().size()
	}

