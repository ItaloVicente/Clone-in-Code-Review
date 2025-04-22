	@Test
	void testPathsResetToNonexistingRef() throws Exception {
		assertThrows(JGitInternalException.class
			git = new Git(db);
			writeTrashFile("a.txt"
			git.add().addFilepattern("a.txt").call();
			assertSameAsHead(
					git.reset().setRef("doesnotexist").addPath("a.txt").call());
		});
