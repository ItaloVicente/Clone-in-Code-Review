	public ObjectLoader open(AnyObjectId objectId
			throws MissingObjectException
			IOException {
		final ObjectReader or = newReader();
		try {
			return or.open(objectId
		} finally {
			or.release();
