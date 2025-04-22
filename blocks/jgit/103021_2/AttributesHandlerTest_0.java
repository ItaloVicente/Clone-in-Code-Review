				attrs("init foo subsub top top_sub"));
		endWalk();
	}

	@Test
	public void testNestedMatchNot() throws Exception {
		setupRepo(null
		writeTrashFile("foo.xml/bar.jar"
		writeTrashFile("foo.xml/bar.xml"
		writeTrashFile("sub/b.jar"
		writeTrashFile("sub/b.xml"
		walk = beginWalk();
		assertIteration(F
		assertIteration(D
		assertIteration(F
		assertIteration(F
		assertIteration(D
		assertIteration(F
		assertIteration(F
		assertIteration(F
		endWalk();
	}

	@Test
	public void testNestedMatch() throws Exception {
		setupRepo(null
		writeTrashFile("foo/bar.jar"
		writeTrashFile("foo/bar.xml"
		writeTrashFile("sub/b.jar"
		writeTrashFile("sub/b.xml"
		writeTrashFile("sub/foo/b.jar"
		walk = beginWalk();
		assertIteration(F
		assertIteration(D
		assertIteration(F
		assertIteration(F
		assertIteration(D
		assertIteration(F
		assertIteration(F
		assertIteration(F
		assertIteration(D
		assertIteration(F
		endWalk();
	}

	@Test
	public void testNestedMatchRecursive() throws Exception {
		setupRepo(null
		writeTrashFile("foo/bar.jar"
		writeTrashFile("foo/bar.xml"
		writeTrashFile("sub/b.jar"
		writeTrashFile("sub/b.xml"
		writeTrashFile("sub/foo/b.jar"
		walk = beginWalk();
		assertIteration(F
		assertIteration(D
		assertIteration(F
		assertIteration(F
		assertIteration(D
		assertIteration(F
		assertIteration(F
		assertIteration(F
		assertIteration(D
		assertIteration(F
		endWalk();
	}

	@Test
	public void testStarMatchOnSlashNot() throws Exception {
		setupRepo(null
		writeTrashFile("sub/a.txt"
		writeTrashFile("foo/sext"
		writeTrashFile("foo/s.txt"
		walk = beginWalk();
		assertIteration(F
		assertIteration(D
		assertIteration(F
		assertIteration(F
		assertIteration(D
		assertIteration(F
		endWalk();
	}

	@Test
	public void testPrefixMatchNot() throws Exception {
		setupRepo(null
		writeTrashFile("sub/new/foo.txt"
		walk = beginWalk();
		assertIteration(F
		assertIteration(D
		assertIteration(F
		assertIteration(D
		assertIteration(F
		endWalk();
	}

	@Test
	public void testComplexPathMatchNot() throws Exception {
		setupRepo(null
		writeTrashFile("sub/new/foo.txt"
		writeTrashFile("sub/ndw"
		walk = beginWalk();
		assertIteration(F
		assertIteration(D
		assertIteration(F
		assertIteration(F
		assertIteration(D
		assertIteration(F
