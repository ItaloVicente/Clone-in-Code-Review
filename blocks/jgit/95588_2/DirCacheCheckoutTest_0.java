	@Test
	public void testLongFilename() throws Exception {
		char[] bytes = new char[253];
		Arrays.fill(bytes
		String longFileName = new String(bytes);
		doit(mkmap(longFileName
				mkmap(longFileName
		writeTrashFile(longFileName
		checkout();
		assertNoConflicts();
		assertUpdated(longFileName);
	}

