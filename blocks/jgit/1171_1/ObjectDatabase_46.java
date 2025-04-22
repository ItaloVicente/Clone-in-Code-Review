	public boolean has(final AnyObjectId objectId) throws IOException {
		final ObjectReader or = newReader();
		try {
			return or.has(objectId);
		} finally {
			or.release();
		}
