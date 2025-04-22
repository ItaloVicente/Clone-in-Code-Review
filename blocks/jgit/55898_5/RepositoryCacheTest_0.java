	@Test
	public void testRepositoryUsageCount() throws Exception {
		FileKey loc = FileKey.exact(db.getDirectory()
		Repository d2 = RepositoryCache.open(loc);
		assertEquals(1
		RepositoryCache.open(FileKey.exact(loc.getFile()
		assertEquals(2
		d2.close();
		assertEquals(1
		d2.close();
		assertEquals(0
	}

	@Test
	public void testRepositoryUsageCountWithRegisteredRepository() {
		assertEquals(1
		RepositoryCache.register(db);
		assertEquals(1
		db.close();
		assertEquals(0
	}
