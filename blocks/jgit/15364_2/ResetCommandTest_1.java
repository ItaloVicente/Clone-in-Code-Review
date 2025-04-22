	@Test
	public void testHardResetOnUnbornBranch() throws Exception {
		git = new Git(db);
		File fileA = writeTrashFile("a.txt"
		git.add().addFilepattern("a.txt").call();
		git.reset().setMode(ResetType.HARD).call();

		DirCache cache = db.readDirCache();
		DirCacheEntry aEntry = cache.getEntry("a.txt");
		assertNull(aEntry);
		assertFalse(fileA.exists());
		assertNull(db.resolve(Constants.HEAD));
	}

