	@Test
	public void testBug508568() throws Exception {
		createFiles("foo.xml/bar.jar"
		writeTrashFile(".gitattributes"
		assertSameAsCGit();
	}

