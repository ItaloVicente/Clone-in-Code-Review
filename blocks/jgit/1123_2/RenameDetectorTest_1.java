	public void testNoRenames_SymlinkAndFileSamePath() throws Exception {
		ObjectId aId = blob("src/dest");

		DiffEntry a = DiffEntry.delete(PATH_A
		DiffEntry b = DiffEntry.add(PATH_A
		a.oldMode = FileMode.SYMLINK;

		rd.add(a);
		rd.add(b);

		List<DiffEntry> entries = rd.compute();
		assertEquals(2
		assertSame(a
		assertSame(b
	}

	public void testSetRenameScore_IllegalArgs() throws Exception {
		try {
			rd.setRenameScore(-1);
			fail();
		} catch (IllegalArgumentException e) {
		}

		try {
			rd.setRenameScore(101);
			fail();
		} catch (IllegalArgumentException e) {
		}
	}

	public void testRenameLimit() throws Exception {
		ObjectId aId = blob("foo\nbar\nbaz\nblarg\n");
		ObjectId bId = blob("foo\nbar\nbaz\nblah\n");
		DiffEntry a = DiffEntry.add(PATH_A
		DiffEntry b = DiffEntry.delete(PATH_Q

		ObjectId cId = blob("a\nb\nc\nd\n");
		ObjectId dId = blob("a\nb\nc\n");
		DiffEntry c = DiffEntry.add(PATH_B
		DiffEntry d = DiffEntry.delete(PATH_H

		rd.add(a);
		rd.add(b);
		rd.add(c);
		rd.add(d);

		rd.setRenameLimit(1);

		assertTrue(rd.isOverRenameLimit());
	}

