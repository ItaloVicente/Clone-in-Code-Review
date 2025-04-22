	public void testExactRename_PathBreaksTie() throws Exception {
		ObjectId foo = blob("foo");

		DiffEntry a = DiffEntry.add("src/com/foo/a.java"
		DiffEntry b = DiffEntry.delete("src/com/foo/b.java"

		DiffEntry c = DiffEntry.add("c.txt"
		DiffEntry d = DiffEntry.delete("d.txt"

		rd.add(a);
		rd.add(d);
		rd.add(b);
		rd.add(c);

		List<DiffEntry> entries = rd.compute();
		assertEquals(2
		assertRename(d
		assertRename(b
	}

	public void testExactRename_OneDeleteManyAdds() throws Exception {
		ObjectId foo = blob("foo");

		DiffEntry a = DiffEntry.add("src/com/foo/a.java"
		DiffEntry b = DiffEntry.add("src/com/foo/b.java"
		DiffEntry c = DiffEntry.add("c.txt"

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

