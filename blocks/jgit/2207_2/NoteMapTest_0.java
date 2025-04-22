	public void testIteratorEmptyMap() {
		Iterator<Note> it = NoteMap.newEmptyMap().iterator();
		assertFalse(it.hasNext());
	}

	public void testIteratorFlatTree() throws Exception {
		RevBlob a = tr.blob("a");
		RevBlob b = tr.blob("b");
		RevBlob data1 = tr.blob("data1");
		RevBlob data2 = tr.blob("data2");
		RevBlob nonNote = tr.blob("non note");

				.add(a.name()
				.add(b.name()
				.add("nonNote"
				.create();
		tr.parseBody(r);

		Iterator it = NoteMap.read(reader
		assertEquals(2
	}

	public void testIteratorFanoutTree2_38() throws Exception {
		RevBlob a = tr.blob("a");
		RevBlob b = tr.blob("b");
		RevBlob data1 = tr.blob("data1");
		RevBlob data2 = tr.blob("data2");
		RevBlob nonNote = tr.blob("non note");

				.add(fanout(2
				.add(fanout(2
				.add("nonNote"
				.create();
		tr.parseBody(r);

		Iterator it = NoteMap.read(reader
		assertEquals(2
	}

	public void testIteratorFanoutTree2_2_36() throws Exception {
		RevBlob a = tr.blob("a");
		RevBlob b = tr.blob("b");
		RevBlob data1 = tr.blob("data1");
		RevBlob data2 = tr.blob("data2");
		RevBlob nonNote = tr.blob("non note");

				.add(fanout(4
				.add(fanout(4
				.add("nonNote"
				.create();
		tr.parseBody(r);

		Iterator it = NoteMap.read(reader
		assertEquals(2
	}

	public void testIteratorFullyFannedOut() throws Exception {
		RevBlob a = tr.blob("a");
		RevBlob b = tr.blob("b");
		RevBlob data1 = tr.blob("data1");
		RevBlob data2 = tr.blob("data2");
		RevBlob nonNote = tr.blob("non note");

				.add(fanout(38
				.add(fanout(38
				.add("nonNote"
				.create();
		tr.parseBody(r);

		Iterator it = NoteMap.read(reader
		assertEquals(2
	}

