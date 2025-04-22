		List<DiffEntry> entries = rd.compute();
		assertEquals(1
		assertRename(b
	}

	public void testExactRename_OneRenameOneModify() throws Exception {
		ObjectId foo = blob("foo");
		ObjectId bar = blob("bar");

		DiffEntry a = DiffEntry.add(PATH_A
		DiffEntry b = DiffEntry.delete(PATH_Q

		DiffEntry c = DiffEntry.modify(PATH_H);
		c.newId = c.oldId = AbbreviatedObjectId.fromObjectId(bar);

		rd.add(a);
		rd.add(b);
		rd.add(c);

		List<DiffEntry> entries = rd.compute();
		assertEquals(2
		assertRename(b
		assertSame(c
	}

	public void testExactRename_ManyRenames() throws Exception {
		ObjectId foo = blob("foo");
		ObjectId bar = blob("bar");

		DiffEntry a = DiffEntry.add(PATH_A
		DiffEntry b = DiffEntry.delete(PATH_Q

		DiffEntry c = DiffEntry.add("README"
		DiffEntry d = DiffEntry.delete("REEDME"

		rd.add(a);
		rd.add(b);
		rd.add(c);
		rd.add(d);

		List<DiffEntry> entries = rd.compute();
		assertEquals(2
		assertRename(d
		assertRename(b
	}

	public void testInexactRename_OnePair() throws Exception {
		ObjectId aId = blob("foo\nbar\nbaz\n");
		ObjectId bId = blob("foo\nbar\nblah\n");

		DiffEntry a = DiffEntry.add(PATH_A
		DiffEntry b = DiffEntry.delete(PATH_Q
