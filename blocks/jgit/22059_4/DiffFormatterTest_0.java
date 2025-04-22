	@Test
	public void testCreateFileHeaderForRenameModeChange()
			throws Exception {
		DiffEntry a = DiffEntry.delete(PATH_A
		DiffEntry b = DiffEntry.add(PATH_B
		b.oldMode = FileMode.REGULAR_FILE;
		b.newMode = FileMode.EXECUTABLE_FILE;
		DiffEntry m = DiffEntry.pair(ChangeType.RENAME
		m.oldId = null;
		m.newId = null;

		FileHeader fh = df.toFileHeader(m);
		String expected = DIFF + "a/src/a b/src/b\n" +
				"old mode 100644\n" +
				"new mode 100755\n" +
				"similarity index 100%\n" +
				"rename from src/a\n" +
				"rename to src/b\n";
		assertEquals(expected
	}

