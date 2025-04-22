	public boolean hasObject(final AnyObjectId objectId) throws IOException {
		final ObjectReader or = newReader();
		try {
			return or.hasObject(objectId);
		} finally {
			or.release();
		}
	}
