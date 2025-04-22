
	@Test
	public void testBracketsInGroup() throws Exception {
		createFiles("["
		writeTrashFile(".gitattributes"
				+ "[[\\]] bar3\n" + "[\\[\\]] bar4\n");
		assertSameAsCGit();
	}
