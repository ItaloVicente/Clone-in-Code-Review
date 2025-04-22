	public ObjectId writeTree(Tree tree) throws IOException {
		try {
			ObjectId id = inserter.insert(OBJ_TREE
			inserter.flush();
			return id;
		} finally {
			inserter.release();
