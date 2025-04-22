	@Test
	public void testLeadingSpaces() throws IOException {
		writeTrashFile("  a/  a"
		writeTrashFile("  a/ a"
		writeTrashFile("  a/a"
		writeTrashFile(" a/  a"
		writeTrashFile(" a/ a"
		writeTrashFile(" a/a"
		writeIgnoreFile(".gitignore"
		writeTrashFile("a/  a"
		writeTrashFile("a/ a"
		writeTrashFile("a/a"

		beginWalk();
		assertEntry(D
		assertEntry(F
		assertEntry(F
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(F
		assertEntry(F
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(F
		assertEntry(F
		endWalk();
	}

	@Test
	public void testTrailingSpaces() throws IOException {
		writeTrashFile("a  /a"
		writeTrashFile("a  /a "
		writeTrashFile("a  /a  "
		writeTrashFile("a /a"
		writeTrashFile("a /a "
		writeTrashFile("a /a  "
		writeTrashFile("a/a"
		writeTrashFile("a/a "
		writeTrashFile("a/a  "

		writeIgnoreFile(".gitignore"

		beginWalk();
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(F
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(F
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(F
		assertEntry(F
		endWalk();
	}

