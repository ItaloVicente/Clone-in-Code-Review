	@Test
	public void testExactRenameAndInexactRename_OneDeleteOneExactOneInexactAdds() throws Exception {
		ObjectId aId = blob("foo\nbar\nbaz\nblarg\n");
		ObjectId bId = blob("foo\nbar\nbaz\nblah\n");

		DiffEntry a = DiffEntry.add("src/com/foo/a.java"
		DiffEntry b = DiffEntry.add("src/com/foo/b.java"

		DiffEntry c = DiffEntry.delete("d.txt"

		rd.add(a);
		rd.add(b);
		rd.add(c);

		List<DiffEntry> entries = rd.compute();
		assertEquals(2

		assertRename(c
		assertCopy(c
	}

	@Test
	public void testExactRenameAndInexactRename_OneDeleteTwoExactOneInexactAdds() throws Exception {
		ObjectId aId = blob("foo\nbar\nbaz\nblarg\n");
		ObjectId bId = blob("foo\nbar\nbaz\nblah\n");

		DiffEntry a = DiffEntry.add("src/com/foo/a.java"
		DiffEntry b = DiffEntry.add("src/com/foo/b.java"
		DiffEntry c = DiffEntry.add("src/com/foo/c.java"

		DiffEntry d = DiffEntry.delete("d.txt"

		rd.add(a);
		rd.add(b);
		rd.add(c);
		rd.add(d);

		List<DiffEntry> entries = rd.compute();
		assertEquals(3

		assertRename(d
		assertCopy(d
		assertCopy(d
	}

	@Test
	public void testExactRenameAndInexactRename_OneDeleteOneExactTwoInexactAdds() throws Exception {
		ObjectId aId = blob("foo\nbar\nbaz\nblarg\n");
		ObjectId bId = blob("foo\nbar\nbaz\nblah\n");

		DiffEntry a = DiffEntry.add("src/com/foo/a.java"
		DiffEntry b = DiffEntry.add("src/com/foo/b.java"
		DiffEntry c = DiffEntry.add("src/com/foo/c.java"

		DiffEntry d = DiffEntry.delete("d.txt"

		rd.add(a);
		rd.add(b);
		rd.add(c);
		rd.add(d);

		List<DiffEntry> entries = rd.compute();
		assertEquals(3

		assertRename(d
		assertCopy(d
		assertCopy(d
	}

