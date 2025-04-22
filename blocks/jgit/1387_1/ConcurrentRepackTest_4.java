		final ObjectInserter inserter = repo.newObjectInserter();
		final ObjectId id;
		try {
			id = inserter.insert(Constants.OBJ_BLOB
			inserter.flush();
		} finally {
			inserter.release();
		}
