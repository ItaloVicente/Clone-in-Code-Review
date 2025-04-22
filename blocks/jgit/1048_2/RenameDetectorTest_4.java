		List<DiffEntry> entries = rd.compute();
		assertEquals(1
		assertRename(b
	}

	public void testNoRenames_SingleByteFiles() throws Exception {
		ObjectId aId = blob("a");
		ObjectId bId = blob("b");

		DiffEntry a = DiffEntry.add(PATH_A
		DiffEntry b = DiffEntry.delete(PATH_Q

		rd.add(a);
		rd.add(b);

		List<DiffEntry> entries = rd.compute();
		assertEquals(2
		assertSame(a
		assertSame(b
	}

	public void testNoRenames_EmptyFile1() throws Exception {
		ObjectId aId = blob("");
		DiffEntry a = DiffEntry.add(PATH_A

		rd.add(a);

		List<DiffEntry> entries = rd.compute();
		assertEquals(1
		assertSame(a
	}

	public void testNoRenames_EmptyFile2() throws Exception {
		ObjectId aId = blob("");
		ObjectId bId = blob("blah");

		DiffEntry a = DiffEntry.add(PATH_A
		DiffEntry b = DiffEntry.delete(PATH_Q
