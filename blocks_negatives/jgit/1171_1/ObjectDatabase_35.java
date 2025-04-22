	 * Fast half of {@link #openObject(WindowCursor, AnyObjectId)}.
	 *
	 * @param curs
	 *            temporary working space associated with the calling thread.
	 * @param objectId
	 *            identity of the object to open.
	 * @return a {@link ObjectLoader} for accessing the data of the named
	 *         object, or null if the object does not exist.
	 * @throws IOException
	 */
	protected abstract ObjectLoader openObject1(WindowCursor curs,
			AnyObjectId objectId) throws IOException;

	/**
	 * Slow half of {@link #openObject(WindowCursor, AnyObjectId)}.
	 *
	 * @param curs
	 *            temporary working space associated with the calling thread.
	 * @param objectName
	 *            name of the object to open.
	 * @param objectId
	 *            identity of the object to open.
	 * @return a {@link ObjectLoader} for accessing the data of the named
	 *         object, or null if the object does not exist.
	 * @throws IOException
	 */
	protected ObjectLoader openObject2(WindowCursor curs, String objectName,
			AnyObjectId objectId) throws IOException {
		return null;
	}

	/**
	 * Open the object from all packs containing it.
