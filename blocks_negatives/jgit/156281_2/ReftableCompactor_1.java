			adjustUpdateIndexes(r);
		}
	}

	/**
	 * Try to add this reader at the bottom of the stack.
	 * <p>
	 * A reader may be rejected by returning {@code false} if the compactor is
	 * already rewriting its {@link #setCompactBytesLimit(long)}. When this
	 * happens the caller should stop trying to add tables, and execute the
	 * compaction.
	 *
	 * @param reader
	 *            the reader to insert at the bottom of the stack. Caller is
	 *            responsible for closing the reader.
	 * @return {@code true} if the compactor accepted this table; {@code false}
	 *         if the compactor has reached its limit.
	 * @throws java.io.IOException
	 *             if size of {@code reader}, or its update indexes cannot be read.
	 */
	public boolean tryAddFirst(ReftableReader reader) throws IOException {
		long sz = reader.size();
		if (compactBytesLimit > 0 && bytesToCompact + sz > compactBytesLimit) {
			return false;
