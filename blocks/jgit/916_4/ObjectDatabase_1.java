	public ObjectLoader openObject(final AnyObjectId objectId)
			throws IOException {
		final ObjectReader or = newReader();
		try {
			return or.openObject(objectId);
		} finally {
			or.release();
		}
	}
