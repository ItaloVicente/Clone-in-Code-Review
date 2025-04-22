		ObjectId id;
		try {
			id = inserter.insert(Constants.OBJ_COMMIT
			inserter.flush();
		} finally {
			inserter.release();
		}
		return pool.lookupCommit(id);
