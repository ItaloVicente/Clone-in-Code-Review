	private ObjectId insertTree(Tree tree) throws IOException {
		try (ObjectInserter oi = db.newObjectInserter()) {
			ObjectId id = oi.insert(Constants.OBJ_TREE
			oi.flush();
			return id;
		}
	}

