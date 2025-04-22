			throws IOException {
		openObjectInAllPacks1(out, curs, objectId);
		for (final ObjectDatabase alt : getAlternates()) {
			alt.openObjectInAllPacks1(out, curs, objectId);
		}
	}

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
	void openObjectInAllPacks1(Collection<PackedObjectLoader> out,
			WindowCursor curs, AnyObjectId objectId) throws IOException {
	}

	/**
	 * @return true if the fast-half search should be tried again.
	 */
	protected boolean tryAgain1() {
		return false;
	}

	/**
	 * Get the alternate databases known to this database.
	 *
	 * @return the alternate list. Never null, but may be an empty array.
	 */
	public final ObjectDatabase[] getAlternates() {
		ObjectDatabase[] r = alternates.get();
		if (r == null) {
			synchronized (alternates) {
				r = alternates.get();
				if (r == null) {
					try {
						r = loadAlternates();
					} catch (IOException e) {
						r = NO_ALTERNATES;
					}
					alternates.set(r);
				}
			}
		}
		return r;
	}

	/**
	 * Load the list of alternate databases into memory.
	 * <p>
	 * This method is invoked by {@link #getAlternates()} if the alternate list
	 * has not yet been populated, or if {@link #closeAlternates()} has been
	 * called on this instance and the alternate list is needed again.
	 * <p>
	 * If the alternate array is empty, implementors should consider using the
	 * constant {@link #NO_ALTERNATES}.
	 *
	 * @return the alternate list for this database.
	 * @throws IOException
	 *             the alternate list could not be accessed. The empty alternate
	 *             array {@link #NO_ALTERNATES} will be assumed by the caller.
	 */
	protected ObjectDatabase[] loadAlternates() throws IOException {
		return NO_ALTERNATES;
	}

	/**
	 * Close the list of alternates returned by {@link #loadAlternates()}.
	 *
	 * @param alt
	 *            the alternate list, from {@link #loadAlternates()}.
	 */
	protected void closeAlternates(ObjectDatabase[] alt) {
		for (final ObjectDatabase d : alt) {
			d.close();
		}
	}
