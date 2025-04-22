	private void assertStageOneToThree(String name) throws Exception {
		DirCache cache = DirCache.read(db.getIndexFile()
		int i = cache.findEntry(name);
		DirCacheEntry stage1 = cache.getEntry(i);
		DirCacheEntry stage2 = cache.getEntry(i + 1);
		DirCacheEntry stage3 = cache.getEntry(i + 2);

		assertEquals(DirCacheEntry.STAGE_1
		assertEquals(DirCacheEntry.STAGE_2
		assertEquals(DirCacheEntry.STAGE_3
