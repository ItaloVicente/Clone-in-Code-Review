	/**
	 * @param curs
	 *            temporary working space associated with the calling thread.
	 * @param id
	 *            SHA-1 of an object.
	 *
	 * @return a {@link ObjectLoader} for accessing the data of the named
	 *         object, or null if the object does not exist.
	 * @throws IOException
	 * @deprecated Use {code newObjectReader().open(id)}.
	 */
	@Deprecated
	public ObjectLoader openObject(ObjectReader curs, AnyObjectId id)
			throws IOException {
		try {
			return curs.openObject(id);
		} catch (MissingObjectException notFound) {
			return null;
		}
	}

