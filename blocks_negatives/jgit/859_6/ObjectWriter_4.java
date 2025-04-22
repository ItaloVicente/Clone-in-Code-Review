	public ObjectId writeCanonicalTree(final byte[] b) throws IOException {
		return writeTree(b.length, new ByteArrayInputStream(b));
	}

	private ObjectId writeTree(final long len, final InputStream is)
			throws IOException {
		return writeObject(Constants.OBJ_TREE, len, is, true);
