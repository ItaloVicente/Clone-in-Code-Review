	@Test
	public void testPathsResetOnUnbornBranch() throws Exception {
		git = new Git(db);
		writeTrashFile("a.txt"
		git.add().addFilepattern("a.txt").call();
		git.reset().addPath("a.txt").call();

		DirCache cache = db.readDirCache();
		DirCacheEntry aEntry = cache.getEntry("a.txt");
		assertNull(aEntry);
	}

	@Test(expected = JGitInternalException.class)
	public void testPathsResetToNonexistingRef() throws Exception {
		git = new Git(db);
		writeTrashFile("a.txt"
		git.add().addFilepattern("a.txt").call();
		git.reset().setRef("doesnotexist").addPath("a.txt").call();
	}

