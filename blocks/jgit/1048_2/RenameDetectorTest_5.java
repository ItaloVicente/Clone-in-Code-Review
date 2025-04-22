		assertSame(a
		assertSame(b
	}

	public void testNoRenames_SymlinkAndFile() throws Exception {
		ObjectId aId = blob("src/dest");

		DiffEntry a = DiffEntry.add(PATH_A
		DiffEntry b = DiffEntry.delete(PATH_Q
		b.oldMode = FileMode.SYMLINK;
