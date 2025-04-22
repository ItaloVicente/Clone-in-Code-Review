	@Test
	public void testExactRename_PathFilters() throws Exception {
		ObjectId foo = blob("foo");
		ObjectId bar = blob("bar");

		DiffEntry a = DiffEntry.add(PATH_A
		DiffEntry b = DiffEntry.delete(PATH_Q

		DiffEntry c = DiffEntry.add(PATH_H
		DiffEntry d = DiffEntry.delete(PATH_B

		rd.add(a);
		rd.add(b);
		rd.add(c);
		rd.add(d);
		rd.setPathFilters(PathFilter.create(PATH_A));

		List<DiffEntry> entries = rd.compute();
		assertEquals("Unexpected entries in: " + entries
		assertRename(b
	}

