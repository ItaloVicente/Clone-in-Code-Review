		ObjectId id1;
		ObjectId id2;
		try (ObjectInserter ins = db.newObjectInserter()) {
			id1 = ins.insert(
					Constants.OBJ_BLOB
			id2 = ins.insert(
					Constants.OBJ_BLOB
			ins.flush();
		}

