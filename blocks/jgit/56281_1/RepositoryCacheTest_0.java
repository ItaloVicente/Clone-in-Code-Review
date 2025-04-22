
	public void testRepositoryUnregisteringWhenClosing() throws Exception {
		FileKey loc = FileKey.exact(db.getDirectory()
		Repository d2 = RepositoryCache.open(loc);
		assertEquals(1
		assertThat(RepositoryCache.getRegisteredKeys()
				hasItem(FileKey.exact(db.getDirectory()
		assertEquals(1

		d2.close();

		assertEquals(0
		assertEquals(0
	}
