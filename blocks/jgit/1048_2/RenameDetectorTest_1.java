		assertRename(b
	}

	public void testInexactRename_OneRenameTwoUnrelatedFiles() throws Exception {
		ObjectId aId = blob("foo\nbar\nbaz\n");
		ObjectId bId = blob("foo\nbar\nblah\n");
		DiffEntry a = DiffEntry.add(PATH_A
		DiffEntry b = DiffEntry.delete(PATH_Q

		ObjectId cId = blob("some\nsort\nof\ntext\n");
		ObjectId dId = blob("completely\nunrelated\ntext\n");
		DiffEntry c = DiffEntry.add("c"
		DiffEntry d = DiffEntry.delete("d"
