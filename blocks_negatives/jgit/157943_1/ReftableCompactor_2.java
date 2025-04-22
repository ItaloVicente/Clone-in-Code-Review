	/**
	 * Set limit on number of bytes from source tables to compact.
	 *
	 * @param bytes
	 *            limit on number of bytes from source tables to compact.
	 * @return {@code this}
	 */
	public ReftableCompactor setCompactBytesLimit(long bytes) {
		compactBytesLimit = bytes;
		return this;
	}

