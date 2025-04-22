	@Test
	public void testExactRename_LargeFile() throws Exception {
		ObjectId aId = blob("blah\nblah\nfoo");

		DiffEntry a = DiffEntry.add(PATH_A
		DiffEntry b = DiffEntry.delete(PATH_Q

		rd.add(a);
		rd.add(b);

		rd.setBigFileThreshold(10);

		List<DiffEntry> entries = rd.compute();
		assertEquals(1
		assertRename(b
	}

	@Test
	public void testInexactRename_LargeFile() throws Exception {

		DiffEntry a = DiffEntry.add(PATH_A
		DiffEntry b = DiffEntry.delete(PATH_Q

		rd.add(a);
		rd.add(b);

		rd.setBigFileThreshold(10);

		List<DiffEntry> entries = rd.compute();

		assertEquals(2
		assertAdd(PATH_A
		assertDelete(PATH_Q
	}

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
	public void testInexactRename_BinaryFile() throws Exception {
		ObjectId aId = blob("a\nb\nc\n\0\0\0\0d\n");
		ObjectId bId = blob("a\nb\nc\n\0\0\0d\n");

		DiffEntry a = DiffEntry.add(PATH_A
		DiffEntry b = DiffEntry.delete(PATH_Q

		rd.add(a);
		rd.add(b);

		rd.setRenameScore(40);

		List<DiffEntry> entries = rd.compute();

		assertEquals(2
		assertAdd(PATH_A
		assertDelete(PATH_Q
	}

