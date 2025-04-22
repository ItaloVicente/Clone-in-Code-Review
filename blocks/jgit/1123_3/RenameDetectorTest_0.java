		assertRename(b
		assertRename(d
	}

	public void testExactRename_MultipleIdenticalDeletes() throws Exception {
		ObjectId foo = blob("foo");

		DiffEntry a = DiffEntry.delete(PATH_A
		DiffEntry b = DiffEntry.delete(PATH_B

		DiffEntry c = DiffEntry.delete(PATH_H
		DiffEntry d = DiffEntry.add(PATH_Q

		rd.add(a);
		rd.add(b);
		rd.add(c);
		rd.add(d);

		List<DiffEntry> entries = rd.compute();
		assertEquals(3
		assertEquals(b
		assertEquals(c
		assertRename(a
