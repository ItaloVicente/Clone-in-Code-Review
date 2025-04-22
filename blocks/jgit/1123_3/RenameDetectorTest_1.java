	public void testInexactRename_NewlinesOnly() throws Exception {
		ObjectId aId = blob("\n\n\n");
		ObjectId bId = blob("\n\n\n\n");

		DiffEntry a = DiffEntry.add(PATH_A
		DiffEntry b = DiffEntry.delete(PATH_Q

		rd.add(a);
		rd.add(b);

		List<DiffEntry> entries = rd.compute();
		assertEquals(1
		assertRename(b
	}

