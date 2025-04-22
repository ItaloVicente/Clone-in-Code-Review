	/**
	 * Compute a hash code for a single line.
	 *
	 * @param raw
	 *            the raw file content.
	 * @param ptr
	 *            first byte of the content line to hash.
	 * @param end
	 *            1 past the last byte of the content line.
	 * @return hash code for the region <code>[ptr, end)</code> of raw.
	 */
	protected int hashLine(final byte[] raw, int ptr, final int end) {
		int hash = 5381;
		for (; ptr < end; ptr++)
			hash = (hash << 5) ^ (raw[ptr] & 0xff);
		return hash;
	}

