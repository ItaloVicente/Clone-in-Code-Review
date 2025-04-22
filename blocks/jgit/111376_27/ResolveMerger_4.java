	private ObjectId insertMergeResult(TemporaryBuffer buf
			Attributes attributes) throws IOException {
		InputStream in = buf.openInputStream();
		try (LfsInputStream is = LfsHelper.getCleanFiltered(getRepository()
				buf.length()
			return getObjectInserter().insert(OBJ_BLOB
