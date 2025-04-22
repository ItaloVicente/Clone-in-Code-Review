	@Test
	public void testGarbageSelectivelyVisible() throws IOException {
		ObjectInserter ins = db.newObjectInserter();
		ObjectId fooId = ins.insert(Constants.OBJ_BLOB
		ins.flush();
		assertEquals(1

		db.getObjectDatabase().listPacks().get(0).setPackSource(PackSource.UNREACHABLE_GARBAGE);

		assertTrue(db.getObjectDatabase().has(fooId));
		assertFalse(db.getObjectDatabase().has(fooId
	}

	@Test
	public void testInserterIgnoresUnreachable() throws IOException {
		ObjectInserter ins = db.newObjectInserter();
		ObjectId fooId = ins.insert(Constants.OBJ_BLOB
		ins.flush();
		assertEquals(1

		db.getObjectDatabase().listPacks().get(0).setPackSource(PackSource.UNREACHABLE_GARBAGE);

		assertFalse(db.getObjectDatabase().has(fooId

		ins.insert(Constants.OBJ_BLOB
		ins.flush();
		assertTrue(db.getObjectDatabase().has(fooId

		DfsReader reader = new DfsReader(db.getObjectDatabase());
		DfsPackFile packs[] = db.getObjectDatabase().getPacks();
		Set<PackSource> pack_sources = new HashSet<PackSource>();

		assertEquals(2

		pack_sources.add(packs[0].getPackDescription().getPackSource());
		pack_sources.add(packs[1].getPackDescription().getPackSource());

		assertTrue(packs[0].hasObject(reader
		assertTrue(packs[1].hasObject(reader
		assertTrue(pack_sources.contains(PackSource.UNREACHABLE_GARBAGE));
		assertTrue(pack_sources.contains(PackSource.INSERT));
	}

