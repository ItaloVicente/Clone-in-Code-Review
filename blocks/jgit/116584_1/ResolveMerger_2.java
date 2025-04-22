			throw e;
		}
		return buf;
	}

	private ObjectId insertMergeResult(TemporaryBuffer buf) throws IOException {
		try (InputStream in = buf.openInputStream()) {
			return getObjectInserter().insert(OBJ_BLOB
