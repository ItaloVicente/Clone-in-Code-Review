
	@Test
	public void testSimpleRootGitIgnoreGlobalNegation1() throws Exception {
		createFiles("x1"
		writeTrashFile(".gitignore"
		assertSameAsCGit();
	}

	@Test
	public void testRepeatedNegationInDifferentFiles5() throws Exception {
		createFiles("a/b/e/nothere.o");
		writeTrashFile(".gitignore"
		writeTrashFile("a/.gitignore"
		writeTrashFile("a/b/.gitignore"
		assertSameAsCGit();
	}

	@Test
	public void testRepeatedNegationInDifferentFilesWithWildmatcher1()
			throws Exception {
		createFiles("e"
		writeTrashFile(".gitignore"
		writeTrashFile("a/.gitignore"
		assertSameAsCGit();
	}

	@Test
	public void testRepeatedNegationInDifferentFilesWithWildmatcher2()
			throws Exception {
		createFiles("e"
				"a/b/dir/l"
				"a/q/dir/dir/s"
		writeTrashFile(".gitignore"
		writeTrashFile("a/.gitignore"
		writeTrashFile("a/b/.gitignore"
		writeTrashFile("c/.gitignore"
		assertSameAsCGit();
	}

	@Test
	public void testNegationAllExceptJavaInSrcAndExceptChildDirInSrc()
			throws Exception {
		createFiles("nothere.o"
				"src/a/keep.java"
		writeTrashFile(".gitignore"
		writeTrashFile("src/.gitignore"
		assertSameAsCGit();
	}
