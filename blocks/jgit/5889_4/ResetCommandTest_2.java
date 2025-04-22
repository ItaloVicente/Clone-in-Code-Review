	@Test
	public void testMixedResetRetainsSizeAndModifiedTime() throws Exception {
		git = new Git(db);

		writeTrashFile("a.txt"
				System.currentTimeMillis() - 60 * 1000);
		assertNotNull(git.add().addFilepattern("a.txt").call());
		assertNotNull(git.commit().setMessage("a commit").call());

		writeTrashFile("b.txt"
				System.currentTimeMillis() - 60 * 1000);
		assertNotNull(git.add().addFilepattern("b.txt").call());
		RevCommit commit2 = git.commit().setMessage("b commit").call();
		assertNotNull(commit2);

		DirCache cache = db.readDirCache();

		DirCacheEntry aEntry = cache.getEntry("a.txt");
		assertNotNull(aEntry);
		assertTrue(aEntry.getLength() > 0);
		assertTrue(aEntry.getLastModified() > 0);

		DirCacheEntry bEntry = cache.getEntry("b.txt");
		assertNotNull(bEntry);
		assertTrue(bEntry.getLength() > 0);
		assertTrue(bEntry.getLastModified() > 0);

		git.reset().setMode(ResetType.MIXED).setRef(commit2.getName()).call();

		cache = db.readDirCache();

		DirCacheEntry mixedAEntry = cache.getEntry("a.txt");
		assertNotNull(mixedAEntry);
		assertEquals(aEntry.getLastModified()
		assertEquals(aEntry.getLastModified()

		DirCacheEntry mixedBEntry = cache.getEntry("b.txt");
		assertNotNull(mixedBEntry);
		assertEquals(bEntry.getLastModified()
		assertEquals(bEntry.getLastModified()
	}

