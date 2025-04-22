	public ObjectId writeCommit(Commit commit) throws IOException {
		try {
			ObjectId id = inserter.insert(OBJ_COMMIT
			inserter.flush();
			return id;
		} finally {
			inserter.release();
