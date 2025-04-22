	@Test
	public void testCreateFileHeaderWithoutIndexLine() throws Exception {
		DiffEntry m = DiffEntry.modify(PATH_A);
		m.oldMode = FileMode.REGULAR_FILE;
		m.newMode = FileMode.EXECUTABLE_FILE;

		FileHeader fh = df.toFileHeader(m);
				"new mode 100755\n";
		assertEquals(expected
	}

