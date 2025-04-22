
	@Test
	public void testDirectoryMatchSubRecursiveBacktrack() throws Exception {
		createFiles("src/new/foo.txt"
		writeTrashFile(".gitignore"
		assertSameAsCGit();
	}

	@Test
	public void testDirectoryMatchSubRecursiveBacktrack2() throws Exception {
		createFiles("src/new/foo.txt"
		writeTrashFile(".gitignore"
		assertSameAsCGit();
	}

	@Test
	public void testDirectoryMatchSubRecursiveBacktrack3() throws Exception {
		createFiles("x/a/a/b/foo.txt");
		writeTrashFile(".gitignore"
		assertSameAsCGit();
	}

	@Test
	public void testDirectoryMatchSubRecursiveBacktrack4() throws Exception {
		createFiles("x/a/a/b/foo.txt"
				"x/y/a/a/a/a/b/foo.txt"
		writeTrashFile(".gitignore"
		assertSameAsCGit();
	}

	@Test
	public void testDirectoryMatchSubRecursiveBacktrack5() throws Exception {
		createFiles("x/a/a/b/foo.txt"
		writeTrashFile(".gitignore"
		assertSameAsCGit();
	}

