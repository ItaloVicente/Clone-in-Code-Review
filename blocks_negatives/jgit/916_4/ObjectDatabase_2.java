	public abstract ObjectLoader openObject(WindowCursor curs,
			AnyObjectId objectId) throws IOException;

	/**
	 * Open the object from all packs containing it.
	 *
	 * @param out
	 *            result collection of loaders for this object, filled with
	 *            loaders from all packs containing specified object
	 * @param curs
	 *            temporary working space associated with the calling thread.
	 * @param objectId
	 *            id of object to search for
	 * @throws IOException
	 */
	abstract void openObjectInAllPacks(final Collection<PackedObjectLoader> out,
			final WindowCursor curs, final AnyObjectId objectId)
			throws IOException;
