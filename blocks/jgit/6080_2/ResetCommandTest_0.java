	@Test
	public void testHardResetOnTag() throws Exception {
		setupRepository();
		String tagName = "initialtag";
		git.tag().setName(tagName).setObjectId(secondCommit)
				.setMessage("message").call();

		DirCacheEntry preReset = DirCache.read(db.getIndexFile()
				.getEntry(indexFile.getName());
		assertNotNull(preReset);

		git.add().addFilepattern(untrackedFile.getName()).call();

		git.reset().setRef(tagName).setMode(HARD).call();

		ObjectId head = db.resolve(Constants.HEAD);
		assertTrue(head.equals(secondCommit));
	}

