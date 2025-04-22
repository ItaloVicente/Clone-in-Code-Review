	private ObjectId insertMergeResult(TemporaryBuffer buf,
			Attributes attributes) throws IOException {
		InputStream in = buf.openInputStream();
		try (LfsInputStream is = LfsFactory.getInstance().applyCleanFilter(
				getRepository(), in,
				buf.length(), attributes.get(Constants.ATTR_MERGE))) {
			return getObjectInserter().insert(OBJ_BLOB, is.getLength(), is);
		}
	}

