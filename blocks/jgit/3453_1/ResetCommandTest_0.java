	@Test
	public void testPathsReset() throws Exception {
		setupRepository();

		DirCacheEntry preReset = DirCache.read(db.getIndexFile()
				.getEntry(indexFile.getName());
		assertNotNull(preReset);

		git.add().addFilepattern(untrackedFile.getName()).call();

		git.reset().addFile(indexFile.getName())
				.addFile(untrackedFile.getName()).call();

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

