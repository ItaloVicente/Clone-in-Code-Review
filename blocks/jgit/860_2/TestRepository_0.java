		ObjectId id;
		try {
			id = inserter.insert(Constants.OBJ_BLOB
			inserter.flush();
		} finally {
			inserter.release();
		}
		return pool.lookupBlob(id);
