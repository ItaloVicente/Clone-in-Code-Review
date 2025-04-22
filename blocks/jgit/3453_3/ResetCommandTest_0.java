	@Test
	public void testPathsReset() throws Exception {
		setupRepository();

		DirCacheEntry preReset = DirCache.read(db.getIndexFile()
				.getEntry(indexFile.getName());
		assertNotNull(preReset);

		git.add().addFilepattern(untrackedFile.getName()).call();

		git.reset().addPath(indexFile.getName())
				.addPath(untrackedFile.getName()).call();

		DirCacheEntry postReset = DirCache.read(db.getIndexFile()
				.getEntry(indexFile.getName());
		assertNotNull(postReset);
		Assert.assertNotSame(preReset.getObjectId()
		Assert.assertEquals(prestage.getObjectId()

		ObjectId head = db.resolve(Constants.HEAD);
		assertTrue(head.equals(secondCommit));
		assertTrue(untrackedFile.exists());
		assertTrue(indexFile.exists());
		assertTrue(inHead(indexFile.getName()));
		assertTrue(inIndex(indexFile.getName()));
		assertFalse(inIndex(untrackedFile.getName()));
	}

	@Test
	public void testPathsResetWithRef() throws Exception {
		setupRepository();

		DirCacheEntry preReset = DirCache.read(db.getIndexFile()
				.getEntry(indexFile.getName());
		assertNotNull(preReset);

		git.add().addFilepattern(untrackedFile.getName()).call();

		git.reset().setRef(initialCommit.getName())
				.addPath(indexFile.getName())
				.addPath(untrackedFile.getName()).call();

		ObjectId head = db.resolve(Constants.HEAD);
		assertTrue(head.equals(secondCommit));
		assertTrue(untrackedFile.exists());
		assertTrue(indexFile.exists());
		assertTrue(inHead(indexFile.getName()));
		assertFalse(inIndex(indexFile.getName()));
		assertFalse(inIndex(untrackedFile.getName()));
	}

