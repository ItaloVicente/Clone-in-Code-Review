	@Test
	public void testRepeatedNegationInDifferentFiles5() throws IOException {
		writeIgnoreFile(".gitignore"
		writeIgnoreFile("a/.gitignore"
		writeIgnoreFile("a/b/.gitignore"
		writeTrashFile("a/b/e/nothere.o"

		beginWalk();
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(D
		assertEntry(F
		endWalk();
	}

	@Test
	public void testIneffectiveNegationDifferentLevels1() throws IOException {
		writeIgnoreFile(".gitignore"
		writeTrashFile("a/b/e/nothere.o"

		beginWalk();
		assertEntry(F
		assertEntry(D
		assertEntry(D
		assertEntry(D
		assertEntry(F
		endWalk();
	}

	@Test
	public void testIneffectiveNegationDifferentLevels2() throws IOException {
		writeIgnoreFile(".gitignore"
		writeIgnoreFile("a/.gitignore"
		writeTrashFile("a/b/e/nothere.o"

		beginWalk();
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(D
		assertEntry(D
		assertEntry(F
		endWalk();
	}

	@Test
	public void testIneffectiveNegationDifferentLevels3() throws IOException {
		writeIgnoreFile(".gitignore"
		writeIgnoreFile("a/b/.gitignore"
		writeTrashFile("a/b/e/nothere.o"

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
	public void testIneffectiveNegationDifferentLevels4() throws IOException {
		writeIgnoreFile(".gitignore"
		writeIgnoreFile("a/b/e/.gitignore"
		writeTrashFile("a/b/e/nothere.o"

		beginWalk();
		assertEntry(F
		assertEntry(D
		assertEntry(D
		assertEntry(D
		assertEntry(F
		assertEntry(F
		endWalk();
	}

	@Test
	public void testIneffectiveNegationDifferentLevels5() throws IOException {
		writeIgnoreFile("a/.gitignore"
		writeIgnoreFile("a/b/.gitignore"
		writeTrashFile("a/b/e/nothere.o"

		beginWalk();
		assertEntry(D
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(D
		assertEntry(F
		endWalk();
	}

