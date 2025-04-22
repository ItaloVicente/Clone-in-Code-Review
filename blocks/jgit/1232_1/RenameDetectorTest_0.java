	public void testInexactRename_SameContentMultipleTimes() throws Exception {
		ObjectId aId = blob("a\na\na\na\n");
		ObjectId bId = blob("a\na\na\n");

		DiffEntry a = DiffEntry.add(PATH_A
		DiffEntry b = DiffEntry.delete(PATH_Q

		rd.add(a);
		rd.add(b);

		List<DiffEntry> entries = rd.compute();
		assertEquals(1
		assertRename(b
	}

