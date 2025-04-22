	@Test
	public void testSimpleRootGitIgnoreGlobalIgnore() throws IOException {
		writeIgnoreFile(".gitignore"

		writeTrashFile("a/x/file"
		writeTrashFile("b/x"
		writeTrashFile("x/file"

		beginWalk();
		assertEntry(F
		assertEntry(D
		assertEntry(D
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(D
		assertEntry(F
		endWalk();
	}

	@Test
	public void testSimpleRootGitIgnoreGlobalDirIgnore() throws IOException {
		writeIgnoreFile(".gitignore"

		writeTrashFile("a/x/file"
		writeTrashFile("x/file"

		beginWalk();
		assertEntry(F
		assertEntry(D
		assertEntry(D
		assertEntry(F
		assertEntry(D
		assertEntry(F
		endWalk();
	}

	@Test
	public void testSimpleRootGitIgnoreWildMatcher() throws IOException {
		writeIgnoreFile(".gitignore"

		writeTrashFile("a/x"
		writeTrashFile("y"

		beginWalk();
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(F
		endWalk();
	}

	@Test
	public void testSimpleRootGitIgnoreWildMatcherDirOnly() throws IOException {
		writeIgnoreFile(".gitignore"

		writeTrashFile("a/x"
		writeTrashFile("y"

		beginWalk();
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(F
		endWalk();
	}

	@Test
	public void testSimpleRootGitIgnoreGlobalNegation1() throws IOException {
		writeIgnoreFile(".gitignore"
		writeTrashFile("x1"
		writeTrashFile("a/x2"
		writeTrashFile("x3/y"

		beginWalk();
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(F
		assertEntry(D
		assertEntry(F
		endWalk();
	}

	@Test
	public void testSimpleRootGitIgnoreGlobalNegation2() throws IOException {
		writeIgnoreFile(".gitignore"
		writeTrashFile("x1"
		writeTrashFile("a/x2"
		writeTrashFile("x3/y"

		beginWalk();
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(F
		assertEntry(D
		assertEntry(F
		endWalk();
	}

	@Test
	public void testSimpleRootGitIgnoreGlobalNegation3() throws IOException {
		writeIgnoreFile(".gitignore"
		writeTrashFile("x1"
		writeTrashFile("a/x2"
		writeTrashFile("x3/y"

		beginWalk();
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(F
		assertEntry(D
		assertEntry(F
		endWalk();
	}

	@Test
	public void testSimpleRootGitIgnoreGlobalNegation4() throws IOException {
		writeIgnoreFile(".gitignore"
		writeTrashFile("x1"
		writeTrashFile("a/x2"
		writeTrashFile("x3/y"

		beginWalk();
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(F
		assertEntry(D
		assertEntry(F
		endWalk();
	}

