	public void testCreateFromEmpty() throws Exception {
		RevBlob a = tr.blob("a");
		RevBlob b = tr.blob("b");
		RevBlob data1 = tr.blob("data1");
		RevBlob data2 = tr.blob("data2");

		NoteMap map = NoteMap.newEmptyMap();
		assertFalse("no a"
		assertFalse("no b"

		map.set(a
		map.set(b

		assertEquals(data1
		assertEquals(data2

		map.remove(a);
		map.remove(b);

		assertFalse("no a"
		assertFalse("no b"

		map.set(a
		assertEquals(data1

		map.set(a
		assertFalse("no a"
	}

	public void testEditFlat() throws Exception {
		RevBlob a = tr.blob("a");
		RevBlob b = tr.blob("b");
		RevBlob data1 = tr.blob("data1");
		RevBlob data2 = tr.blob("data2");

				.add(a.name()
				.add(b.name()
				.create();
		tr.parseBody(r);

		NoteMap map = NoteMap.read(reader
		map.set(a
		map.set(b
		map.set(data1
		map.set(data2

		assertEquals(data2
		assertEquals(b
		assertFalse("no b"
		assertFalse("no data2"

		MutableObjectId id = new MutableObjectId();
		for (int p = 42; p > 0; p--) {
			id.setByte(1
			map.set(id
		}

		for (int p = 42; p > 0; p--) {
			id.setByte(1
			assertTrue("contains " + id
		}
	}

	public void testEditFanout2_38() throws Exception {
		RevBlob a = tr.blob("a");
		RevBlob b = tr.blob("b");
		RevBlob data1 = tr.blob("data1");
		RevBlob data2 = tr.blob("data2");

				.add(fanout(2
				.add(fanout(2
				.create();
		tr.parseBody(r);

		NoteMap map = NoteMap.read(reader
		map.set(a
		map.set(b
		map.set(data1
		map.set(data2

		assertEquals(data2
		assertEquals(b
		assertFalse("no b"
		assertFalse("no data2"

		map.set(a
		map.set(data1
		assertFalse("no a"
		assertFalse("no data1"
	}

