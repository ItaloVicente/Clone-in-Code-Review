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
