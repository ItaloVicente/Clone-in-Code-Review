
		assertEntry(D
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(F
		assertEntry(F
		endWalk();
	}

	@Test
	public void testNegateAllExceptJavaInSrc() throws IOException {
		writeIgnoreFile(".gitignore"
		writeTrashFile("nothere.o"

		writeIgnoreFile("src/.gitignore"

		writeTrashFile("src/keep.java"
		writeTrashFile("src/nothere.o"
		writeTrashFile("src/a/nothere.o"

		beginWalk();
		assertEntry(F
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(F
		assertEntry(F
		endWalk();
	}

	@Test
	public void testNegationAllExceptJavaInSrcAndExceptChildDirInSrc()
			throws IOException {
		writeIgnoreFile(".gitignore"
		writeTrashFile("nothere.o"

		writeIgnoreFile("src/.gitignore"

		writeTrashFile("src/keep.java"
		writeTrashFile("src/nothere.o"
		writeTrashFile("src/a/keep.java"
		writeTrashFile("src/a/keep.o"

		beginWalk();
		assertEntry(F
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(F
		assertEntry(F
		assertEntry(F
		endWalk();
	}

	@Test
	public void testRepeatedNegation() throws IOException {
		writeIgnoreFile(".gitignore"

		writeTrashFile("e/nothere.o"

		beginWalk();
		assertEntry(F
		assertEntry(D
		assertEntry(F
		endWalk();
	}

	@Test
	public void testRepeatedNegationInDifferentFiles1() throws IOException {
		writeIgnoreFile(".gitignore"

		writeIgnoreFile("e/.gitignore"
		writeTrashFile("e/nothere.o"

		beginWalk();
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(F
		endWalk();
	}

	@Test
	public void testRepeatedNegationInDifferentFiles2() throws IOException {
		writeIgnoreFile(".gitignore"

		writeIgnoreFile("a/.gitignore"
		writeTrashFile("a/e/nothere.o"

		beginWalk();
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(D
		assertEntry(F
		endWalk();
	}

	@Test
	public void testRepeatedNegationInDifferentFiles3() throws IOException {
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
	public void testRepeatedNegationInDifferentFiles4() throws IOException {
		writeIgnoreFile(".gitignore"

		writeIgnoreFile("a/.gitignore"
		writeIgnoreFile("a/b/.gitignore"
		writeIgnoreFile("a/b/c/.gitignore"
		writeTrashFile("a/b/c/e/nothere.o"

		beginWalk();
		assertEntry(F
		assertEntry(D
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
	public void testEmptyIgnoreNode() {
		IgnoreNode node = new IgnoreNode();
		assertEquals(MatchResult.CHECK_PARENT
		assertEquals(MatchResult.CHECK_PARENT
		assertEquals(MatchResult.CHECK_PARENT_NEGATE_FIRST_MATCH
				node.isIgnored(""
