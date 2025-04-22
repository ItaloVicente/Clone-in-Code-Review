	 * @return size of the index, in bytes. If 0 the index size is not yet
	 *         known.
	 */
	public long getIndexSize() {
		return indexSize;
	}

	/**
	 * @param bytes
	 *            size of the index in bytes. If 0 the size is not known and
	 *            will be determined on first read.
	 * @return {@code this}
