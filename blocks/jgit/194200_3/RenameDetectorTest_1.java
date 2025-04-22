	@Test
	public void testInexactRename_SinglePathFilter() throws Exception {
		ObjectId aId = blob("foo\nbar\nbaz\nblarg\n");
		ObjectId bId = blob("foo\nbar\nbaz\nblah\n");
		DiffEntry a = DiffEntry.add(PATH_A
		DiffEntry b = DiffEntry.delete(PATH_Q

		ObjectId cId = blob("some\nsort\nof\ntext\n");
		ObjectId dId = blob("completely\nunrelated\ntext\n");
		DiffEntry c = DiffEntry.add(PATH_B
		DiffEntry d = DiffEntry.delete(PATH_H

		rd.add(a);
		rd.add(b);
		rd.add(c);
		rd.add(d);
		rd.setSourceFilter(PathFilter.create(PATH_A));

		List<DiffEntry> entries = rd.compute();
		assertEquals(2
		assertRename(b
		assertSame(d
	}

