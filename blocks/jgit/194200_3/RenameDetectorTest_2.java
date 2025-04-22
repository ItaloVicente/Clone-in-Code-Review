	@Test
	public void testNoRenames_SourcePathFilter() throws Exception {
		ObjectId aId = blob("");
		ObjectId bId = blob("blah1");
		ObjectId cId = blob("");
		ObjectId dId = blob("blah2");

		DiffEntry a = DiffEntry.add(PATH_A
		DiffEntry b = DiffEntry.delete(PATH_Q

		DiffEntry c = DiffEntry.add(PATH_H
		DiffEntry d = DiffEntry.delete(PATH_B

		rd.add(a);
		rd.add(b);
		rd.add(c);
		rd.add(d);
		rd.setSourceFilter(PathFilter.create(PATH_A));

		List<DiffEntry> entries = rd.compute();
		assertEquals(3
		assertSame(a
		assertSame(d
		assertSame(b
	}

