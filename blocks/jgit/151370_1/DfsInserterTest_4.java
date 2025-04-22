		try (ObjectInserter ins = db.newObjectInserter()) {
			ObjectId id1 = ins.insert(Constants.OBJ_BLOB
					Constants.encode("foo"));
			ins.flush();
			ObjectId id2 = ins.insert(Constants.OBJ_BLOB
					Constants.encode("bar"));
			String abbr1 = ObjectId.toString(id1).substring(0
			String abbr2 = ObjectId.toString(id2).substring(0
			assertFalse(abbr1.equals(abbr2));

			try (ObjectReader reader = ins.newReader()) {
				assertSame(ins
				Collection<ObjectId> objs;
				objs = reader.resolve(AbbreviatedObjectId.fromString(abbr1));
				assertEquals(1
				assertEquals(id1

				objs = reader.resolve(AbbreviatedObjectId.fromString(abbr2));
				assertEquals(1
				assertEquals(id2
			}
		}
