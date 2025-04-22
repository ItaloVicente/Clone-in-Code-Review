	/**
	 * Open object in all packs containing specified object.
	 *
	 * @param objectId
	 *            id of object to search for
	 * @param curs
	 *            temporary working space associated with the calling thread.
	 * @return collection of loaders for this object, from all packs containing
	 *         this object
	 * @throws IOException
	 */
	public abstract Collection<PackedObjectLoader> openObjectInAllPacks(
			AnyObjectId objectId, WindowCursor curs)
			throws IOException;

	/**
	 * Open object in all packs containing specified object.
	 *
	 * @param objectId
	 *            id of object to search for
	 * @param resultLoaders
	 *            result collection of loaders for this object, filled with
	 *            loaders from all packs containing specified object
	 * @param curs
	 *            temporary working space associated with the calling thread.
	 * @throws IOException
	 */
	abstract void openObjectInAllPacks(final AnyObjectId objectId,
			final Collection<PackedObjectLoader> resultLoaders,
			final WindowCursor curs) throws IOException;

