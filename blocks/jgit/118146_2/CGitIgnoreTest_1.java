
	@Test
	public void testBackslashAtFilenameEnd()
			throws Exception {
		assumeTrue(File.separatorChar != '\\');
		createFiles("a/backslash"
		writeTrashFile("a/.gitignore"
		createFiles("b/backslash"
		writeTrashFile("b/.gitignore"
		createFiles("c/backslash"
		writeTrashFile("c/.gitignore"
		assertSameAsCGit();
	}
