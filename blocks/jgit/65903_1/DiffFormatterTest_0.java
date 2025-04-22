	@Test
	public void testCreateFileHeader_AddGitLink() throws Exception {
		ObjectId adId = blob("a\nd\n");
		DiffEntry ent = DiffEntry.add("FOO"
		ent.newMode = FileMode.GITLINK;
		FileHeader fh = df.toFileHeader(ent);

				+ "new file mode " + GITLINK + "\n"
				+ "index "
				+ ObjectId.zeroId().abbreviate(8).name()
				+ ".."
				+ "+++ b/FOO\n";
		assertEquals(diffHeader

		assertEquals(1
		HunkHeader hh = fh.getHunks().get(0);

		EditList el = hh.toEditList();
		assertEquals(1

		Edit e = el.get(0);
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(1
		assertEquals(Edit.Type.INSERT
	}

	@Test
	public void testCreateFileHeader_DeleteGitLink() throws Exception {
		ObjectId adId = blob("a\nd\n");
		DiffEntry ent = DiffEntry.delete("FOO"
		ent.oldMode = FileMode.GITLINK;
		FileHeader fh = df.toFileHeader(ent);

				+ "deleted file mode " + GITLINK + "\n"
				+ "index "
				+ adId.abbreviate(8).name()
				+ ".."
				+ "+++ /dev/null\n";
		assertEquals(diffHeader

		assertEquals(1
		HunkHeader hh = fh.getHunks().get(0);

		EditList el = hh.toEditList();
		assertEquals(1

		Edit e = el.get(0);
		assertEquals(0
		assertEquals(1
		assertEquals(0
		assertEquals(0
		assertEquals(Edit.Type.DELETE
	}

