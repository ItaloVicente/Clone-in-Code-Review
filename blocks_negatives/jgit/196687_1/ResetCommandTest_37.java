	@Test(expected = JGitInternalException.class)
	public void testPathsResetToNonexistingRef() throws Exception {
		git = new Git(db);
		writeTrashFile("a.txt", "content");
		git.add().addFilepattern("a.txt").call();
		assertSameAsHead(
				git.reset().setRef("doesnotexist").addPath("a.txt").call());
