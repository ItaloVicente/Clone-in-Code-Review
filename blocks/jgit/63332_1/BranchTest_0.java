
	@Test
	public void testRenameBranch() throws Exception {
		String[] out = execute("git branch -m master slave");
		assertArrayOfLinesEquals(new String[] { "" }
		out = execute("git branch -a");
		assertArrayOfLinesEquals(new String[] { "* slave"
	}

	@Test
	public void testRenameBranchSingleName() throws Exception {
		String[] out = execute("git branch -m slave");
		assertArrayOfLinesEquals(new String[] { "" }
		out = execute("git branch -a");
		assertArrayOfLinesEquals(new String[] { "* slave"
	}
