	private ObjectId insertEmptyBlob() throws IOException {
		final ObjectId emptyId;
		ObjectInserter oi = db.newObjectInserter();
		try {
			emptyId = oi.insert(Constants.OBJ_BLOB
			oi.flush();
		} finally {
			oi.release();
		}
		return emptyId;
	}

	private ObjectId insertTree(Tree tree) throws IOException {
		ObjectInserter oi = db.newObjectInserter();
		try {
			ObjectId id = oi.insert(Constants.OBJ_TREE
			oi.flush();
			return id;
		} finally {
			oi.release();
		}
	}

