	public void testBreakModify_BreakAll() throws Exception {
		ObjectId aId = blob("foo");
		ObjectId bId = blob("bar");

		DiffEntry m = DiffEntry.modify(PATH_A);
		m.oldId = AbbreviatedObjectId.fromObjectId(aId);
		m.newId = AbbreviatedObjectId.fromObjectId(bId);

		DiffEntry a = DiffEntry.add(PATH_B

		rd.add(a);
		rd.add(m);

		rd.setModifyScore(101);

		List<DiffEntry> entries = rd.compute();
		assertEquals(2
		assertAdd(PATH_A
		assertRename(DiffEntry.breakModify(m).get(0)
	}

	public void testBreakModify_BreakNone() throws Exception {
		ObjectId aId = blob("foo");
		ObjectId bId = blob("bar");

		DiffEntry m = DiffEntry.modify(PATH_A);
		m.oldId = AbbreviatedObjectId.fromObjectId(aId);
		m.newId = AbbreviatedObjectId.fromObjectId(bId);

		DiffEntry a = DiffEntry.add(PATH_B

		rd.add(a);
		rd.add(m);

		rd.setModifyScore(-1);

		List<DiffEntry> entries = rd.compute();
		assertEquals(2
		assertSame(m
		assertSame(a
	}

	public void testBreakModify_BreakBelowScore() throws Exception {
		ObjectId aId = blob("foo");
		ObjectId bId = blob("bar");

		DiffEntry m = DiffEntry.modify(PATH_A);
		m.oldId = AbbreviatedObjectId.fromObjectId(aId);
		m.newId = AbbreviatedObjectId.fromObjectId(bId);

		DiffEntry a = DiffEntry.add(PATH_B

		rd.add(a);
		rd.add(m);


		List<DiffEntry> entries = rd.compute();
		assertEquals(2
		assertAdd(PATH_A
		assertRename(DiffEntry.breakModify(m).get(0)
	}

	public void testBreakModify_DontBreakAboveScore() throws Exception {
		ObjectId aId = blob("blah\nblah\nfoo");
		ObjectId bId = blob("blah\nblah\nbar");

		DiffEntry m = DiffEntry.modify(PATH_A);
		m.oldId = AbbreviatedObjectId.fromObjectId(aId);
		m.newId = AbbreviatedObjectId.fromObjectId(bId);

		DiffEntry a = DiffEntry.add(PATH_B

		rd.add(a);
		rd.add(m);


		List<DiffEntry> entries = rd.compute();
		assertEquals(2
		assertSame(m
		assertSame(a
	}

	public void testBreakModify_RejoinIfUnpaired() throws Exception {
		ObjectId aId = blob("foo");
		ObjectId bId = blob("bar");

		DiffEntry m = DiffEntry.modify(PATH_A);
		m.oldId = AbbreviatedObjectId.fromObjectId(aId);
		m.newId = AbbreviatedObjectId.fromObjectId(bId);

		rd.add(m);


		List<DiffEntry> entries = rd.compute();
		assertEquals(1
		assertEquals(m
	}

