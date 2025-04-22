	private ObjectId insertTree(TreeFormatter tree) throws IOException {
		ObjectInserter oi = db.newObjectInserter();
		try {
			ObjectId id = oi.insert(tree);
			oi.flush();
			return id;
		} finally {
			oi.release();
		}
	}

	private ObjectId insertCommit(final CommitBuilder builder)
			throws IOException
