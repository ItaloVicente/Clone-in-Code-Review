		final ObjectId oldTree
		try (ObjectInserter inserter = db.newObjectInserter()) {
			final ObjectId aFileId = inserter.insert(OBJ_BLOB
			bFileId = inserter.insert(OBJ_BLOB
			cFileId1 = inserter.insert(OBJ_BLOB
			cFileId2 = inserter.insert(OBJ_BLOB
