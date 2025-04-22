	/**
	 * Seek to a timestamp in a reference's log.
	 *
	 * @param refName
	 *            exact name of the reference whose log to read.
	 * @param timeUsec
	 *            time in microseconds since the epoch to scan backwards from.
	 *            Records at this time and older will be returned.
	 * @throws IOException
	 *             reftable cannot be read.
	 */
