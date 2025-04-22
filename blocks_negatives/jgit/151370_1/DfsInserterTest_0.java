		ObjectReader reader = ins.newReader();
		assertSame(ins, reader.getCreatedFromInserter());
		assertEquals("foo", readString(reader.open(id1)));
		assertEquals("bar", readString(reader.open(id2)));
		assertEquals(0, db.getObjectDatabase().listPacks().size());
		ins.flush();
		assertEquals(1, db.getObjectDatabase().listPacks().size());
