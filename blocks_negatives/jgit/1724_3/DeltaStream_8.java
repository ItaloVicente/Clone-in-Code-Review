	/**
	 * Open the base stream.
	 * <p>
	 * The {@code DeltaStream} may close and reopen the base stream multiple
	 * times if copy instructions use offsets out of order. This can occur if a
	 * large block in the file was moved from near the top, to near the bottom.
	 * In such cases the reopened stream is skipped to the target offset, so
	 * {@code skip(long)} should be as efficient as possible.
	 *
	 * @return stream to read from the base object. This stream should not be
	 *         buffered (or should be only minimally buffered), and does not
	 *         need to support mark/reset.
	 * @throws IOException
	 *             the base object cannot be opened for reading.
	 */
	protected abstract InputStream openBase() throws IOException;

	/**
	 * @return length of the base object, in bytes.
	 * @throws IOException
	 *             the length of the base cannot be determined.
	 */
	protected abstract long getBaseSize() throws IOException;

