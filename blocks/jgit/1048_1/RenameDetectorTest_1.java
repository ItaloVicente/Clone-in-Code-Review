	public void testGetEntriesHeuristicRenames() throws Exception {
		ObjectId aId = testDb.blob("foo\nbar\nbaz\n").copy();
		ObjectId bId = testDb.blob("foo\nbar\nblah\n").copy();

		DiffEntry a = new DiffEntry();
		a.newId = AbbreviatedObjectId.fromObjectId(aId);
		a.newMode = FileMode.REGULAR_FILE;
		a.newName = "some/file.c";
		a.changeType = ChangeType.ADD;

		DiffEntry b = new DiffEntry();
		b.oldId = AbbreviatedObjectId.fromObjectId(bId);
		b.oldMode = FileMode.REGULAR_FILE;
		b.oldName = "some/other_file.c";
		b.changeType = ChangeType.DELETE;

		rd.addDiffEntry(a);
		rd.addDiffEntry(b);

		List<DiffEntry> entries = rd.getEntries();
		assertEquals(1

		DiffEntry somefile = entries.get(0);
		assertNotNull(somefile);
		assertTrue(aId.equals(somefile.newId.toObjectId()));
		assertTrue(bId.equals(somefile.oldId.toObjectId()));
		assertEquals(FileMode.REGULAR_FILE
		assertEquals(FileMode.REGULAR_FILE
		assertEquals(ChangeType.RENAME
		assertEquals("some/file.c"
		assertEquals("some/other_file.c"
		assertEquals(67
	}

	public void testGetEntriesMultipleHeuristicRenames() throws Exception {
		ObjectId aId = testDb.blob("foo\nbar\nbaz\n").copy();
		ObjectId bId = testDb.blob("foo\nbar\nblah\n").copy();
		ObjectId cId = testDb.blob("some\nsort\nof\ntext\n").copy();
		ObjectId dId = testDb.blob("completely\nunrelated\ntext\n").copy();

		DiffEntry a = new DiffEntry();
		a.newId = AbbreviatedObjectId.fromObjectId(aId);
		a.newMode = FileMode.REGULAR_FILE;
		a.newName = "some/file.c";
		a.changeType = ChangeType.ADD;

		DiffEntry b = new DiffEntry();
		b.oldId = AbbreviatedObjectId.fromObjectId(bId);
		b.oldMode = FileMode.REGULAR_FILE;
		b.oldName = "some/other_file.c";
		b.changeType = ChangeType.DELETE;

		DiffEntry c = new DiffEntry();
		c.newId = AbbreviatedObjectId.fromObjectId(cId);
		c.newMode = FileMode.REGULAR_FILE;
		c.newName = "c";
		c.changeType = ChangeType.ADD;

		DiffEntry d = new DiffEntry();
		d.oldId = AbbreviatedObjectId.fromObjectId(dId);
		d.oldMode = FileMode.REGULAR_FILE;
		d.oldName = "d";
		d.changeType = ChangeType.DELETE;

		rd.addDiffEntry(a);
		rd.addDiffEntry(b);
		rd.addDiffEntry(c);
		rd.addDiffEntry(d);

		List<DiffEntry> entries = rd.getEntries();
		assertEquals(3

		assertEquals(c

		assertEquals(d

		DiffEntry somefile = entries.get(2);
		assertNotNull(somefile);
		assertTrue(aId.equals(somefile.newId.toObjectId()));
		assertTrue(bId.equals(somefile.oldId.toObjectId()));
		assertEquals(FileMode.REGULAR_FILE
		assertEquals(FileMode.REGULAR_FILE
		assertEquals(ChangeType.RENAME
		assertEquals("some/file.c"
		assertEquals("some/other_file.c"
		assertEquals(67
	}

	public void testGetEntriesEmptyFile() throws Exception {
		ObjectId aId = testDb.blob("").copy();

		DiffEntry a = new DiffEntry();
		a.newId = AbbreviatedObjectId.fromObjectId(aId);
		a.newMode = FileMode.REGULAR_FILE;
		a.newName = "some/file.c";
		a.changeType = ChangeType.ADD;

		rd.addDiffEntry(a);

		List<DiffEntry> entries = rd.getEntries();
		assertEquals(1

		assertEquals(a
	}

	public void testGetEntriesEmptyFile2() throws Exception {
		ObjectId aId = testDb.blob("").copy();
		ObjectId bId = testDb.blob("blah").copy();

		DiffEntry a = new DiffEntry();
		a.newId = AbbreviatedObjectId.fromObjectId(aId);
		a.newMode = FileMode.REGULAR_FILE;
		a.newName = "some/file.c";
		a.changeType = ChangeType.ADD;

		DiffEntry b = new DiffEntry();
		b.oldId = AbbreviatedObjectId.fromObjectId(bId);
		b.oldMode = FileMode.REGULAR_FILE;
		b.oldName = "some/other_file.c";
		b.changeType = ChangeType.DELETE;

		rd.addDiffEntry(a);
		rd.addDiffEntry(b);

		List<DiffEntry> entries = rd.getEntries();
		assertEquals(2

		assertEquals(a
		assertEquals(b
	}

	public void testGetEntriesHeuristicRenamesLastByteDifferent()
			throws Exception {
		ObjectId aId = testDb.blob("foo\nbar\na").copy();
		ObjectId bId = testDb.blob("foo\nbar\nb").copy();

		DiffEntry a = new DiffEntry();
		a.newId = AbbreviatedObjectId.fromObjectId(aId);
		a.newMode = FileMode.REGULAR_FILE;
		a.newName = "some/file.c";
		a.changeType = ChangeType.ADD;

		DiffEntry b = new DiffEntry();
		b.oldId = AbbreviatedObjectId.fromObjectId(bId);
		b.oldMode = FileMode.REGULAR_FILE;
		b.oldName = "some/other_file.c";
		b.changeType = ChangeType.DELETE;

		rd.addDiffEntry(a);
		rd.addDiffEntry(b);

		List<DiffEntry> entries = rd.getEntries();
		assertEquals(1

		DiffEntry somefile = entries.get(0);
		assertNotNull(somefile);
		assertTrue(aId.equals(somefile.newId.toObjectId()));
		assertTrue(bId.equals(somefile.oldId.toObjectId()));
		assertEquals(FileMode.REGULAR_FILE
		assertEquals(FileMode.REGULAR_FILE
		assertEquals(ChangeType.RENAME
		assertEquals("some/file.c"
		assertEquals("some/other_file.c"
		assertEquals(67
	}

	public void testGetEntriesSingleByteFiles() throws Exception {
		ObjectId aId = testDb.blob("a").copy();
		ObjectId bId = testDb.blob("b").copy();

		DiffEntry a = new DiffEntry();
		a.newId = AbbreviatedObjectId.fromObjectId(aId);
		a.newMode = FileMode.REGULAR_FILE;
		a.newName = "some/file.c";
		a.changeType = ChangeType.ADD;

		DiffEntry b = new DiffEntry();
		b.oldId = AbbreviatedObjectId.fromObjectId(bId);
		b.oldMode = FileMode.REGULAR_FILE;
		b.oldName = "some/other_file.c";
		b.changeType = ChangeType.DELETE;

		rd.addDiffEntry(a);
		rd.addDiffEntry(b);

		List<DiffEntry> entries = rd.getEntries();
		assertEquals(2

		assertEquals(a
		assertEquals(b
	}

	public void testGetEntriesSameBucket() throws Exception {
		RenameDetectorForTesting testRd = new RenameDetectorForTesting(db);

		ObjectId aId = testDb.blob("ab\nab\nab\nac\nad\nae\n").copy();
		ObjectId bId = testDb.blob("ac\nab\nab\nab\naa\na0\na1\n").copy();

		DiffEntry a = new DiffEntry();
		a.newId = AbbreviatedObjectId.fromObjectId(aId);
		a.newMode = FileMode.REGULAR_FILE;
		a.newName = "some/file.c";
		a.changeType = ChangeType.ADD;

		DiffEntry b = new DiffEntry();
		b.oldId = AbbreviatedObjectId.fromObjectId(bId);
		b.oldMode = FileMode.REGULAR_FILE;
		b.oldName = "some/other_file.c";
		b.changeType = ChangeType.DELETE;

		testRd.addDiffEntry(a);
		testRd.addDiffEntry(b);

		List<DiffEntry> entries = testRd.getEntries();
		assertEquals(1

		DiffEntry somefile = entries.get(0);
		assertNotNull(somefile);
		assertTrue(aId.equals(somefile.newId.toObjectId()));
		assertTrue(bId.equals(somefile.oldId.toObjectId()));
		assertEquals(FileMode.REGULAR_FILE
		assertEquals(FileMode.REGULAR_FILE
		assertEquals(ChangeType.RENAME
		assertEquals("some/file.c"
		assertEquals("some/other_file.c"
		assertEquals(62
	}
