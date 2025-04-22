	 * @return a {@link ObjectLoader} for accessing the data of the named
	 *         object, or null if the object does not exist.
	 * @throws IOException
	 */
	public ObjectLoader openObject(final AnyObjectId id)
			throws IOException {
		try {
			return getObjectDatabase().openObject(id);
		} catch (MissingObjectException notFound) {
			return null;
		}
	}

	/**
	 * @param id
	 *            SHA'1 of a blob
	 * @return an {@link ObjectLoader} for accessing the data of a named blob
