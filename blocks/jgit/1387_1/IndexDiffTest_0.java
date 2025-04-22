
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
