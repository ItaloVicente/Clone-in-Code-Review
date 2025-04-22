		try (ObjectInserter ins = db.newObjectInserter()) {
			ObjectId id1 = ins.insert(Constants.OBJ_BLOB
					Constants.encode("foo"));
			ins.flush();
			ObjectId id2 = ins.insert(Constants.OBJ_BLOB
					Constants.encode("bar"));
			assertEquals(1

			try (ObjectReader reader = ins.newReader()) {
				assertSame(ins
				assertEquals("foo"
				assertEquals("bar"
				assertEquals(1
			}
			ins.flush();
			assertEquals(2
		}
