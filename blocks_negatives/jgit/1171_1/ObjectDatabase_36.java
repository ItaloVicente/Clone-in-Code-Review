	 * If any alternates are present, their packs are also considered.
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
	final void openObjectInAllPacks(final Collection<PackedObjectLoader> out,
			final WindowCursor curs, final AnyObjectId objectId)
			throws IOException {
		openObjectInAllPacks1(out, curs, objectId);
		for (final ObjectDatabase alt : getAlternates()) {
			alt.openObjectInAllPacks1(out, curs, objectId);
		}
	}

	/**
	 * Open the object from all packs containing it.
