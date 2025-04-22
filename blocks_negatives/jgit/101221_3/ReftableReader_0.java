	/**
	 * @param deletes
	 *            if {@code true} deleted references will be returned. If
	 *            {@code false} (default behavior), deleted references will be
	 *            skipped, and not returned.
	 */
	public void setIncludeDeletes(boolean deletes) {
		includeDeletes = deletes;
	}

	/**
	 * Seek to the first reference in the file, to iterate in order.
	 *
	 * @throws IOException
	 *             reftable cannot be read.
	 */
