	@Test
	public void testDirectoryMatchSubRecursiveBacktrack() throws Exception {
		createFiles("src/new/foo.txt"
		writeTrashFile(".gitattributes"
		assertSameAsCGit();
	}

	@Test
	public void testDirectoryMatchSubRecursiveBacktrack2() throws Exception {
		createFiles("src/new/foo.txt"
		writeTrashFile(".gitattributes"
		assertSameAsCGit();
	}

	@Test
	public void testDirectoryMatchSubRecursiveBacktrack3() throws Exception {
		createFiles("src/new/src/new/foo.txt"
				"foo/src/new/bar/src/new/foo.txt");
		writeTrashFile(".gitattributes"
		assertSameAsCGit();
	}

	@Test
	public void testDirectoryMatchSubRecursiveBacktrack4() throws Exception {
		createFiles("src/src/src/new/foo.txt"
				"foo/src/src/bar/src/new/foo.txt");
		writeTrashFile(".gitattributes"
		assertSameAsCGit();
	}

	@Test
	public void testDirectoryMatchSubRecursiveBacktrack5() throws Exception {
		createFiles("x/a/a/b/foo.txt"
				"x/y/a/a/a/a/b/foo.txt"
		writeTrashFile(".gitattributes"
		assertSameAsCGit();
	}

	@Test
	public void testDirectoryMatchSubRecursiveBacktrack6() throws Exception {
		createFiles("x/a/a/b/foo.txt"
		writeTrashFile(".gitattributes"
		assertSameAsCGit();
	}

