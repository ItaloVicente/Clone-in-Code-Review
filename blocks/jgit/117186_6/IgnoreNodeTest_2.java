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

