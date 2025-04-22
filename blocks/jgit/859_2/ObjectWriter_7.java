	public ObjectId writeTag(Tag tag) throws IOException {
		try {
			ObjectId id = inserter.insert(OBJ_TAG
			inserter.flush();
			return id;
		} finally {
			inserter.release();
		}
