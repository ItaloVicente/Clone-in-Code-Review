	@Test
	public void testPathsResetOnDirs() throws Exception {
		setupRepository();

		DirCacheEntry preReset = DirCache.read(db.getIndexFile()
				.getEntry("dir/b.txt");
		assertNotNull(preReset);

		git.add().addFilepattern(untrackedFile.getName()).call();

		git.reset().addPath("dir").call();

		DirCacheEntry postReset = DirCache.read(db.getIndexFile()
				.getEntry("dir/b.txt");
		assertNotNull(postReset);
		Assert.assertNotSame(preReset.getObjectId()

		ObjectId head = db.resolve(Constants.HEAD);
		assertTrue(head.equals(secondCommit));
		assertTrue(untrackedFile.exists());
		assertTrue(inHead("dir/b.txt"));
		assertTrue(inIndex("dir/b.txt"));
	}

