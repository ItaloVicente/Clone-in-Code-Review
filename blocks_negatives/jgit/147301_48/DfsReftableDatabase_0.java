	/**
	 * Obtain a handle to the merged reader.
	 *
	 * @return (possibly cached) handle to the merged reader.
	 * @throws java.io.IOException
	 *             if tables cannot be opened.
	 */
	protected Reftable reader() throws IOException {
		lock.lock();
		try {
			if (mergedTables == null) {
				mergedTables = new MergedReftable(stack().readers());
			}
			return mergedTables;
		} finally {
			lock.unlock();
		}
	}
