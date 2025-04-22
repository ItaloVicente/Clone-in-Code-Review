	public void testRepositoryUsageCountWithRegisteredRepository() {
		assertEquals(1, ((Repository) db).useCnt.get());
		RepositoryCache.register(db);
		assertEquals(1, ((Repository) db).useCnt.get());
		db.close();
		assertEquals(0, ((Repository) db).useCnt.get());
