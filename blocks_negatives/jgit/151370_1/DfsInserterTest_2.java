		ObjectInserter ins = db.newObjectInserter();
		ObjectId id1 = ins.insert(Constants.OBJ_BLOB, Constants.encode("foo"));
		ins.flush();
		ObjectId id2 = ins.insert(Constants.OBJ_BLOB, Constants.encode("bar"));
		String abbr1 = ObjectId.toString(id1).substring(0, 4);
		String abbr2 = ObjectId.toString(id2).substring(0, 4);
		assertFalse(abbr1.equals(abbr2));

		ObjectReader reader = ins.newReader();
		assertSame(ins, reader.getCreatedFromInserter());
		Collection<ObjectId> objs;
		objs = reader.resolve(AbbreviatedObjectId.fromString(abbr1));
		assertEquals(1, objs.size());
		assertEquals(id1, objs.iterator().next());

		objs = reader.resolve(AbbreviatedObjectId.fromString(abbr2));
		assertEquals(1, objs.size());
		assertEquals(id2, objs.iterator().next());
