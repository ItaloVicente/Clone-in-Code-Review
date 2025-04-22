	public void testWriteUnchangedFlat() throws Exception {
		RevBlob a = tr.blob("a");
		RevBlob b = tr.blob("b");
		RevBlob data1 = tr.blob("data1");
		RevBlob data2 = tr.blob("data2");

				.add(a.name()
				.add(b.name()
				.add(".gitignore"
				.add("zoo-animals.txt"
				.create();
		tr.parseBody(r);

		NoteMap map = NoteMap.read(reader
		assertTrue("has note for a"
		assertTrue("has note for b"

		RevCommit n = commitNoteMap(map);
		assertNotSame("is new commit"
		assertSame("same tree"
	}

	public void testWriteUnchangedFanout2_38() throws Exception {
		RevBlob a = tr.blob("a");
		RevBlob b = tr.blob("b");
		RevBlob data1 = tr.blob("data1");
		RevBlob data2 = tr.blob("data2");

				.add(fanout(2
				.add(fanout(2
				.add(".gitignore"
				.add("zoo-animals.txt"
				.create();
		tr.parseBody(r);

		NoteMap map = NoteMap.read(reader
		assertTrue("has note for a"
		assertTrue("has note for b"

		RevCommit n = commitNoteMap(map);
		assertNotSame("is new commit"
		assertSame("same tree"

		map = NoteMap.read(reader
		n = commitNoteMap(map);
		assertNotSame("is new commit"
		assertSame("same tree"
	}

