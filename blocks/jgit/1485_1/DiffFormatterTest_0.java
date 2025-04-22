	public void testCreateFileHeader_Add() throws Exception {
		ObjectId adId = blob("a\nd\n");
		DiffEntry ent = DiffEntry.add("FOO"
		FileHeader fh = df.createFileHeader(ent);

				+ "new file mode " + REGULAR_FILE + "\n"
				+ "index "
				+ ObjectId.zeroId().abbreviate(8).name()
				+ ".."
				+ "+++ b/FOO\n";
		assertEquals(diffHeader

		assertEquals(0
		assertEquals(fh.getBuffer().length
		assertEquals(FileHeader.PatchType.UNIFIED

		assertEquals(1

		HunkHeader hh = fh.getHunks().get(0);
		assertEquals(1

		EditList el = hh.toEditList();
		assertEquals(1

		Edit e = el.get(0);
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(2
		assertEquals(Edit.Type.INSERT
	}

	public void testCreateFileHeader_Delete() throws Exception {
		ObjectId adId = blob("a\nd\n");
		DiffEntry ent = DiffEntry.delete("FOO"
		FileHeader fh = df.createFileHeader(ent);

				+ "deleted file mode " + REGULAR_FILE + "\n"
				+ "index "
				+ adId.abbreviate(8).name()
				+ ".."
				+ "+++ /dev/null\n";
		assertEquals(diffHeader

		assertEquals(0
		assertEquals(fh.getBuffer().length
		assertEquals(FileHeader.PatchType.UNIFIED

		assertEquals(1

		HunkHeader hh = fh.getHunks().get(0);
		assertEquals(1

		EditList el = hh.toEditList();
		assertEquals(1

		Edit e = el.get(0);
		assertEquals(0
		assertEquals(2
		assertEquals(0
		assertEquals(0
		assertEquals(Edit.Type.DELETE
	}

