
	@Test
	public void testUpdateSmudgedEntries() throws Exception {
		git.branchCreate().setName("test2").call();
		RefUpdate rup = db.updateRef(Constants.HEAD);
		rup.link("refs/heads/test2");

		File file = new File(db.getWorkTree()
		long size = file.length();
		long mTime = file.lastModified() - 5000L;
		assertTrue(file.setLastModified(mTime));

		DirCache cache = DirCache.lock(db.getIndexFile()
		DirCacheEntry entry = cache.getEntry("Test.txt");
		assertNotNull(entry);
		entry.setLength(0);
		entry.setLastModified(0);
		cache.write();
		assertTrue(cache.commit());

		cache = DirCache.read(db.getIndexFile()
		entry = cache.getEntry("Test.txt");
		assertNotNull(entry);
		assertEquals(0
		assertEquals(0

		db.getIndexFile().setLastModified(
				db.getIndexFile().lastModified() - 5000);

		assertNotNull(git.checkout().setName("test").call());

		cache = DirCache.read(db.getIndexFile()
		entry = cache.getEntry("Test.txt");
		assertNotNull(entry);
		assertEquals(size
		assertEquals(mTime
	}
