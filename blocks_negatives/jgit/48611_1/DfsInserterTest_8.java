		ObjectInserter ins = db.newObjectInserter();
		ins.insert(Constants.OBJ_BLOB, Constants.encode("foo"));
		ins.insert(Constants.OBJ_BLOB, Constants.encode("bar"));
		assertEquals(0, db.getObjectDatabase().listPacks().size());

		ins.release();
