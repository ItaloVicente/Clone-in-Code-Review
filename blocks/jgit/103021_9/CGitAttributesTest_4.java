
	@Test
	public void testPrefixMatchNot() throws Exception {
		createFiles("src/new/foo.txt");
		writeTrashFile(".gitattributes"
		assertSameAsCGit();
	}

	@Test
	public void testComplexPathMatchNot() throws Exception {
		createFiles("src/new/foo.txt"
		writeTrashFile(".gitattributes"
		assertSameAsCGit();
	}

	@Test
	public void testStarPathMatchNot() throws Exception {
		createFiles("src/new/foo.txt"
		writeTrashFile(".gitattributes"
		assertSameAsCGit();
	}

	@Test
	public void testDirectoryMatchSubSimple() throws Exception {
		createFiles("src/new/foo.txt"
		writeTrashFile(".gitattributes"
		assertSameAsCGit();
	}

	@Test
	public void testDirectoryMatchSubRecursive() throws Exception {
		createFiles("src/new/foo.txt"
		writeTrashFile(".gitattributes"
		assertSameAsCGit();
	}

	@Test
	public void testDirectoryMatchSubComplex() throws Exception {
		createFiles("src/new/foo.txt"
		writeTrashFile(".gitattributes"
		assertSameAsCGit();
	}

	@Test
	public void testDirectoryMatch() throws Exception {
		createFiles("src/new/foo.txt"
		writeTrashFile(".gitattributes"
		assertSameAsCGit();
	}
