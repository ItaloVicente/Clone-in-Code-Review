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
