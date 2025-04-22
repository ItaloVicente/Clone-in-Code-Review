
	@Test
	public void testSkipWorktree() throws Exception {
		TestRepository<Repository> db_t = new TestRepository<>(db);
		DirCacheEntry e;
		DirCacheBuilder dcBuilder = db.lockDirCache().builder();

		writeTrashFile("a"
		e = db_t.file("a"
		dcBuilder.add(e);

		writeTrashFile("b"
		e = db_t.file("b"
		e.setSkipWorkTree(true);
		dcBuilder.add(e);

		writeTrashFile("c"
		e = db_t.file("c"
		e.setSkipWorkTree(false);
		dcBuilder.add(e);

		dcBuilder.commit();

		String result = toString(execute("git status"));

		assertEquals(toString("On branch master"
				"new file:   a"
	}
