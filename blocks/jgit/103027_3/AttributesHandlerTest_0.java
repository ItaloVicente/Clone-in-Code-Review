	@Test
	public void testDirectoryMatchSubRecursiveBacktrack2() throws Exception {
		setupRepo(null
		writeTrashFile("sub/new/foo.txt"
		writeTrashFile("foo/sub/new/foo.txt"
		writeTrashFile("sub/sub/new/foo.txt"
		walk = beginWalk();
		assertIteration(F
		assertIteration(D
		assertIteration(D
		assertIteration(D
		assertIteration(F
		assertIteration(D
		assertIteration(F
		assertIteration(D
		assertIteration(F
		assertIteration(D
		assertIteration(D
		assertIteration(F
		endWalk();
	}

