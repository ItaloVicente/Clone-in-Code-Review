	@Test
	public void testUnescapedBracketsInGroup() throws Exception {
		createFiles("["
		writeTrashFile(".gitignore"
		assertSameAsCGit();
	}

	@Test
	public void testEscapedFirstBracketInGroup() throws Exception {
		createFiles("["
		writeTrashFile(".gitignore"
		assertSameAsCGit();
	}

	@Test
	public void testEscapedSecondBracketInGroup() throws Exception {
		createFiles("["
		writeTrashFile(".gitignore"
		assertSameAsCGit();
	}

	@Test
	public void testEscapedBothBracketsInGroup() throws Exception {
		createFiles("["
		writeTrashFile(".gitignore"
		assertSameAsCGit();
	}
