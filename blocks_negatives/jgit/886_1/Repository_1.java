	/**
	 * @param objectId
	 * @return true if the specified object is stored in this repo or any of the
	 *         known shared repositories.
	 */
	public boolean hasObject(final AnyObjectId objectId) {
		return objectDatabase.hasObject(objectId);
	}

	/**
	 * @param id
	 *            SHA-1 of an object.
	 *
	 * @return a {@link ObjectLoader} for accessing the data of the named
	 *         object, or null if the object does not exist.
	 * @throws IOException
	 */
	public ObjectLoader openObject(final AnyObjectId id)
			throws IOException {
		final WindowCursor wc = new WindowCursor();
		try {
			return openObject(wc, id);
		} finally {
			wc.release();
		}
	}

	/**
	 * @param curs
	 *            temporary working space associated with the calling thread.
	 * @param id
	 *            SHA-1 of an object.
	 *
	 * @return a {@link ObjectLoader} for accessing the data of the named
	 *         object, or null if the object does not exist.
	 * @throws IOException
	 */
	public ObjectLoader openObject(final WindowCursor curs, final AnyObjectId id)
			throws IOException {
		return objectDatabase.openObject(curs, id);
	}

