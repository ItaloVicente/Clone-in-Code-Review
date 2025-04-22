		try (ObjectInserter ins = db.newObjectInserter()) {
			ObjectId id1 = ins.insert(Constants.OBJ_BLOB
					Constants.encode("foo"));
			ObjectId id2 = ins.insert(Constants.OBJ_BLOB
					Constants.encode("bar"));
			assertEquals(0
