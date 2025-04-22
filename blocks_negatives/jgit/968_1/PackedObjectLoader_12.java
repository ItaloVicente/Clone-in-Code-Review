	/**
	 * Peg the pack file open to support data copying.
	 * <p>
	 * Applications trying to copy raw pack data should ensure the pack stays
	 * open and available throughout the entire copy. To do that use:
	 *
	 * <pre>
	 * loader.beginCopyRawData();
	 * try {
	 * 	loader.copyRawData(out, tmpbuf, curs);
	 * } finally {
	 * 	loader.endCopyRawData();
	 * }
	 * </pre>
	 *
	 * @throws IOException
	 *             this loader contains stale information and cannot be used.
	 *             The most likely cause is the underlying pack file has been
	 *             deleted, and the object has moved to another pack file.
	 */
	public void beginCopyRawData() throws IOException {
		pack.beginCopyRawData();
	}

	/**
	 * Copy raw object representation from storage to provided output stream.
	 * <p>
	 * Copied data doesn't include object header. User must provide temporary
	 * buffer used during copying by underlying I/O layer.
	 * </p>
	 *
	 * @param out
	 *            output stream when data is copied. No buffering is guaranteed.
	 * @param buf
	 *            temporary buffer used during copying. Recommended size is at
	 *            least few kB.
	 * @param curs
	 *            temporary thread storage during data access.
	 * @throws IOException
	 *             when the object cannot be read.
	 * @see #beginCopyRawData()
	 */
	public void copyRawData(OutputStream out, byte buf[], WindowCursor curs)
			throws IOException {
		pack.copyRawData(this, out, buf, curs);
	}

	/** Release resources after {@link #beginCopyRawData()}. */
	public void endCopyRawData() {
		pack.endCopyRawData();
	}

	/**
	 * @return true if this loader is capable of fast raw-data copying basing on
	 *         compressed data checksum; false if raw-data copying needs
	 *         uncompressing and compressing data
	 * @throws IOException
	 *             the index file format cannot be determined.
	 */
	public boolean supportsFastCopyRawData() throws IOException {
		return pack.supportsFastCopyRawData();
	}

