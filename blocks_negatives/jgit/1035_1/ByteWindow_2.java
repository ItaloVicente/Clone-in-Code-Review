	/**
	 * Pump bytes into the supplied inflater as input.
	 *
	 * @param pos
	 *            offset within the file to start supplying input from.
	 * @param dstbuf
	 *            destination buffer the inflater should output decompressed
	 *            data to.
	 * @param dstoff
	 *            current offset within <code>dstbuf</code> to inflate into.
	 * @param inf
	 *            the inflater to feed input to. The caller is responsible for
	 *            initializing the inflater as multiple windows may need to
	 *            supply data to the same inflater to completely decompress
	 *            something.
	 * @return updated <code>dstoff</code> based on the number of bytes
	 *         successfully copied into <code>dstbuf</code> by
	 *         <code>inf</code>. If the inflater is not yet finished then
	 *         another window's data must still be supplied as input to finish
	 *         decompression.
	 * @throws DataFormatException
	 *             the inflater encountered an invalid chunk of data. Data
	 *             stream corruption is likely.
	 */
	final int inflate(long pos, byte[] dstbuf, int dstoff, Inflater inf)
			throws DataFormatException {
		return inflate((int) (pos - start), dstbuf, dstoff, inf);
