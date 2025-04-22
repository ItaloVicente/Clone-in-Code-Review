		try {
			ObjectId id = inserter.insert(OBJ_BLOB
			inserter.flush();
			return id;
		} finally {
			inserter.release();
		}
