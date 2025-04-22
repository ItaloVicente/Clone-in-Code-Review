	/**
	 * Seek to a timestamp in a reference's log.
	 *
	 * @param refName
	 *            exact name of the reference whose log to read.
	 * @param time
	 *            time in seconds since the epoch to scan from. Records at this
	 *            time and older will be returned.
	 * @throws IOException
	 *             reftable cannot be read.
	 */
