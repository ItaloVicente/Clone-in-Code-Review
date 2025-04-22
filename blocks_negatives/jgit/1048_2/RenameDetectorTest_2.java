	public void testGetEntriesMultipleRenames() throws Exception {
		ObjectId foo = testDb.blob("foo").copy();
		ObjectId bar = testDb.blob("bar").copy();

		DiffEntry a = new DiffEntry();
		a.newId = AbbreviatedObjectId.fromObjectId(foo);
		a.newMode = FileMode.REGULAR_FILE;
		a.newName = "some/file.c";
		a.changeType = ChangeType.ADD;

		DiffEntry b = new DiffEntry();
		b.oldId = AbbreviatedObjectId.fromObjectId(foo);
		b.oldMode = FileMode.REGULAR_FILE;
		b.oldName = "some/other_file.c";
		b.changeType = ChangeType.DELETE;

		DiffEntry c = new DiffEntry();
		c.newId = AbbreviatedObjectId.fromObjectId(bar);
		c.newMode = FileMode.REGULAR_FILE;
		c.newName = "README";
		c.changeType = ChangeType.ADD;

		DiffEntry d = new DiffEntry();
		d.oldId = AbbreviatedObjectId.fromObjectId(bar);
		d.oldMode = FileMode.REGULAR_FILE;
		d.oldName = "REEDME";
		d.changeType = ChangeType.DELETE;

		rd.addDiffEntry(a);
		rd.addDiffEntry(b);
		rd.addDiffEntry(c);
		rd.addDiffEntry(d);

		List<DiffEntry> entries = rd.getEntries();
