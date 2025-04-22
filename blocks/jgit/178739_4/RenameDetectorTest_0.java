	@Test
	public void testExactRename_BinaryFile() throws Exception {
		ObjectId aId = blob("a\nb\nc\n\0\0\0\0d\n");

		DiffEntry a = DiffEntry.add(PATH_A
		DiffEntry b = DiffEntry.delete(PATH_Q

		rd.add(a);
		rd.add(b);

		List<DiffEntry> entries = rd.compute();
		assertEquals(1
		assertRename(b
	}

	@Test
	public void testInexactRenameForBinaryFile_identifiedByDefault() throws Exception {
		ObjectId aId = blob("a\nb\nc\n\0\0\0\0d\n");
		ObjectId bId = blob("a\nb\nc\n\0\0\0d\n");

		DiffEntry a = DiffEntry.add(PATH_A
		DiffEntry b = DiffEntry.delete(PATH_Q

		rd.add(a);
		rd.add(b);

		rd.setRenameScore(40);

		List<DiffEntry> entries = rd.compute();

		assertEquals(1
		assertRename(b
	}

	@Test
	public void testInexactRenameForBinaryFile_notIdentifiedIfSkipParameterSet() throws Exception {
		ObjectId aId = blob("a\nb\nc\n\0\0\0\0d\n");
		ObjectId bId = blob("a\nb\nc\n\0\0\0d\n");

		DiffEntry a = DiffEntry.add(PATH_A
		DiffEntry b = DiffEntry.delete(PATH_Q

		rd.add(a);
		rd.add(b);

		rd.setRenameScore(40);
		rd.setSkipContentRenamesForBinaryFiles(true);

		List<DiffEntry> entries = rd.compute();

		assertEquals(2
		assertAdd(PATH_A
		assertDelete(PATH_Q
	}

