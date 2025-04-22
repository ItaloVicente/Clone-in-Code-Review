	@Test
	public void testSlashMatchesDirectory() throws IOException {
		writeIgnoreFile(".gitignore"

		writeTrashFile("out1/out1"
		writeTrashFile("out1/out2"
		writeTrashFile("out2/out1"
		writeTrashFile("out2/out2"

		beginWalk();
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(F
		endWalk();
	}

	@Test
	public void testWildcardWithSlashMatchesDirectory() throws IOException {
		writeIgnoreFile(".gitignore"

		writeTrashFile("out1/out1.txt"
		writeTrashFile("out1/out2"
		writeTrashFile("out1/out2.txt"
		writeTrashFile("out1/out2x/a"
		writeTrashFile("out2/out1.txt"
		writeTrashFile("out2/out2.txt"
		writeTrashFile("out2x/out1.txt"
		writeTrashFile("out2x/out2.txt"

		beginWalk();
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(F
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(F
		endWalk();
	}

