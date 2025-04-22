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

	/**
	 * Open object in all packs containing specified object.
