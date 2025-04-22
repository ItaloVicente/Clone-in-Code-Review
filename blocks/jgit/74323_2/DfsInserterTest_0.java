	@Test
	public void testNoCheckExisting() throws IOException {
		byte[] contents = Constants.encode("foo");
		ObjectId fooId;
		try (ObjectInserter ins = db.newObjectInserter()) {
			fooId = ins.insert(Constants.OBJ_BLOB
			ins.flush();
		}
		assertEquals(1

		try (ObjectInserter ins = db.newObjectInserter()) {
			((DfsInserter) ins).checkExisting(false);
			assertEquals(fooId
			ins.flush();
		}
		assertEquals(2

		DfsReader reader = new DfsReader(db.getObjectDatabase());
		DfsPackFile packs[] = db.getObjectDatabase().getPacks();

		assertEquals(2
		DfsPackFile p1 = packs[0];
		assertEquals(PackSource.INSERT
		assertTrue(p1.hasObject(reader

		DfsPackFile p2 = packs[1];
		assertEquals(PackSource.INSERT
		assertTrue(p2.hasObject(reader
	}

