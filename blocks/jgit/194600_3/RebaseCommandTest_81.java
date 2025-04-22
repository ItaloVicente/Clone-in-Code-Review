
	}

	@Test
	public void testRebaseInteractiveFixupWithBlankLines() throws Exception {
		simpleFixup("Add file2"
	}

	@Test
	public void testRebaseInteractiveFixupWithBlankLines2() throws Exception {
		simpleFixup("Add file2\n\nBody\n"
				"updated file1 on master\n\nsome text");
	}

	@Test
	public void testRebaseInteractiveFixupWithHash() throws Exception {
		simpleFixup("#Add file2"
	}

	@Test
	public void testRebaseInteractiveFixupWithHash2() throws Exception {
		simpleFixup("#Add file2\n\nHeader has hash\n"
				"#updated file1 on master");
