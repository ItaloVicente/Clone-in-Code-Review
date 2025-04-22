	public void testNoRenames_GitlinkAndFile() throws Exception {
		ObjectId aId = blob("src/dest");

		DiffEntry a = DiffEntry.add(PATH_A
		DiffEntry b = DiffEntry.delete(PATH_Q
		b.oldMode = FileMode.GITLINK;

		rd.add(a);
		rd.add(b);

		List<DiffEntry> entries = rd.compute();
