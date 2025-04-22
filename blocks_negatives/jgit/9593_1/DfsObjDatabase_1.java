	protected abstract ReadableChannel openPackIndex(DfsPackDescription desc)
			throws FileNotFoundException, IOException;

	/**
	 * Open a pack file for writing.
	 *
	 * @param desc
	 *            description of pack to write. This is an instance previously
	 *            obtained from {@link #newPack(PackSource)}.
	 * @return channel to write the pack file.
	 * @throws IOException
	 *             the file cannot be opened.
	 */
	protected abstract DfsOutputStream writePackFile(DfsPackDescription desc)
			throws IOException;

	/**
	 * Open a pack index for writing.
	 *
	 * @param desc
	 *            description of index to write. This is an instance previously
	 *            obtained from {@link #newPack(PackSource)}.
	 * @return channel to write the index file.
	 * @throws IOException
	 *             the file cannot be opened.
	 */
	protected abstract DfsOutputStream writePackIndex(DfsPackDescription desc)
			throws IOException;
