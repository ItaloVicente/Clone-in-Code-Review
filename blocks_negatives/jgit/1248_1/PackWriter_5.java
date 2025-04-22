	 * Default setting: {@link #DEFAULT_REUSE_DELTAS}
	 * </p>
	 *
	 * @return true if object is configured to reuse deltas; false otherwise.
	 */
	public boolean isReuseDeltas() {
		return reuseDeltas;
	}

	/**
	 * Set reuse deltas configuration option for this writer. When enabled,
	 * writer will search for delta representation of object in repository and
	 * use it if possible. Normally, only deltas with base to another object
	 * existing in set of objects to pack will be used. Exception is however
	 * thin-pack (see
	 * {@link #preparePack(ProgressMonitor, Collection, Collection)} and
	 * {@link #preparePack(Iterator)}) where base object must exist on other
	 * side machine.
	 * <p>
	 * When raw delta data is directly copied from a pack file, checksum is
	 * computed to verify data.
	 * </p>
	 * <p>
	 * Default setting: {@link #DEFAULT_REUSE_DELTAS}
	 * </p>
	 *
	 * @param reuseDeltas
	 *            boolean indicating whether or not try to reuse deltas.
	 */
	public void setReuseDeltas(boolean reuseDeltas) {
		this.reuseDeltas = reuseDeltas;
	}

	/**
	 * Checks whether object is configured to reuse existing objects
	 * representation in repository.
	 * <p>
	 * Default setting: {@link #DEFAULT_REUSE_OBJECTS}
	 * </p>
