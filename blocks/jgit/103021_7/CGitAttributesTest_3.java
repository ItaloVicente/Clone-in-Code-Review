	@Test
	public void testNestedMatch() throws Exception {
		createFiles("foo/bar.jar"
				"sub/foo/b.jar");
		writeTrashFile(".gitattributes"
				"foo/ xml\n" + "sub/foo/ sub\n" + "*.jar jar\n");
		assertSameAsCGit();
	}

	@Test
	public void testNestedMatchWithWildcard() throws Exception {
		createFiles("foo/bar.jar"
				"sub/foo/b.jar");
		writeTrashFile(".gitattributes"
		assertSameAsCGit();
	}

