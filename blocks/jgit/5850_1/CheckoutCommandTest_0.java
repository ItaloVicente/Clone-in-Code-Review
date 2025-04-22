
	@Test
	public void testUpdateSmudgedEntries() throws Exception {
		git.branchCreate().setName("test2").call();
		RefUpdate rup = db.updateRef(Constants.HEAD);
		rup.link("refs/heads/test2");

		File file = new File(db.getWorkTree()
		long size = file.length();
		long mTime = file.lastModified();

		DirCache cache = db.lockDirCache();
		DirCacheEntry entry = cache.getEntry("Test.txt");
		assertNotNull(entry);
		entry.setLength(0);
		entry.setLastModified(0);
		cache.write();
		assertTrue(cache.commit());

		cache = db.readDirCache();
		entry = cache.getEntry("Test.txt");
		assertNotNull(entry);
		assertEquals(0
		assertEquals(0

		db.getIndexFile().setLastModified(
				db.getIndexFile().lastModified() - 5000);

		assertNotNull(git.checkout().setName("test").call());

		cache = db.readDirCache();
		entry = cache.getEntry("Test.txt");
		assertNotNull(entry);
		assertEquals(size
		assertEquals(mTime
	}
