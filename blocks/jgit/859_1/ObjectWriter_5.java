	public ObjectId writeCanonicalTree(byte[] treeData) throws IOException {
		try {
			ObjectId id = inserter.insert(OBJ_TREE
			inserter.flush();
			return id;
		} finally {
			inserter.release();
		}
