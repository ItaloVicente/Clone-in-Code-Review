		ObjectId id;
		try {
			id = inserter.insert(Constants.OBJ_TAG
			inserter.flush();
		} finally {
			inserter.release();
		}
		return (RevTag) pool.lookupAny(id
