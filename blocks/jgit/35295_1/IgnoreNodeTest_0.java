
		assertEntry(D
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(F
		assertEntry(F
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
	}

	@Test
	public void testNegationAllExceptJavaInSrcAndExceptChildDirInSrc()
			throws IOException {
		writeIgnoreFile(".gitignore"
		writeTrashFile("nothere.o"

		writeIgnoreFile("src/.gitignore"

		writeTrashFile("src/keep.java"
		writeTrashFile("src/nothere.o"
		writeTrashFile("src/a/nothere.java"
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
