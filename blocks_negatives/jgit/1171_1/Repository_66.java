	 *            id of object to search for
	 * @param resultLoaders
	 *            result collection of loaders for this object, filled with
	 *            loaders from all packs containing specified object
	 * @param curs
	 *            temporary working space associated with the calling thread.
	 * @throws IOException
	 */
	void openObjectInAllPacks(final AnyObjectId objectId,
			final Collection<PackedObjectLoader> resultLoaders,
			final WindowCursor curs) throws IOException {
		objectDatabase.openObjectInAllPacks(resultLoaders, curs, objectId);
	}

	/**
	 * @param id
	 *            SHA'1 of a blob
	 * @return an {@link ObjectLoader} for accessing the data of a named blob
