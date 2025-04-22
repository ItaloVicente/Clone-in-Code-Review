		ObjectInserter ins = db.newObjectInserter();
		ObjectId id1 = ins.insert(Constants.OBJ_BLOB, Constants.encode("foo"));
		ins.flush();
		ObjectId id2 = ins.insert(Constants.OBJ_BLOB, Constants.encode("bar"));
		assertEquals(1, db.getObjectDatabase().listPacks().size());

		ObjectReader reader = ins.newReader();
		assertSame(ins, reader.getCreatedFromInserter());
		assertEquals("foo", readString(reader.open(id1)));
		assertEquals("bar", readString(reader.open(id2)));
		assertEquals(1, db.getObjectDatabase().listPacks().size());
		ins.flush();
		assertEquals(2, db.getObjectDatabase().listPacks().size());
