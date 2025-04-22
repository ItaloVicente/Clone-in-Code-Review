	@Test
	public void testNegationProblem() throws IOException {
		writeIgnoreFile(".gitignore"
		writeTrashFile("bld/x"
		writeTrashFile("bld/y"

		beginWalk();
		assertEntry(F
		assertEntry(D
		assertEntry(F

		assertEntry(F
		endWalk();
	}

