	@Test
	public void testCreateFileHeaderWithoutIndexLine() throws Exception {
		DiffEntry m = DiffEntry.modify(PATH_A);
		m.oldMode = FileMode.REGULAR_FILE;
		m.newMode = FileMode.EXECUTABLE_FILE;

		FileHeader fh = df.toFileHeader(m);
				"new mode 100755\n";
		assertEquals(expected
	}

	@Test
	public void testCreateFileHeaderForRenameWithoutContentChange() throws Exception {
		DiffEntry a = DiffEntry.delete(PATH_A
		DiffEntry b = DiffEntry.add(PATH_B
		DiffEntry m = DiffEntry.pair(ChangeType.RENAME
		m.oldId = null;
		m.newId = null;

		FileHeader fh = df.toFileHeader(m);
				"rename to src/b\n";
		assertEquals(expected
	}

