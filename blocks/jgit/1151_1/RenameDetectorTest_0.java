	public void testExactRename_DifferentObjects() throws Exception {
		ObjectId foo = blob("foo");
		ObjectId bar = blob("bar");

		DiffEntry a = DiffEntry.add(PATH_A
		DiffEntry h = DiffEntry.add(PATH_H
		DiffEntry q = DiffEntry.delete(PATH_Q

		rd.add(a);
		rd.add(h);
		rd.add(q);

		List<DiffEntry> entries = rd.compute();
		assertEquals(3
		assertSame(a
		assertSame(h
		assertSame(q
	}

