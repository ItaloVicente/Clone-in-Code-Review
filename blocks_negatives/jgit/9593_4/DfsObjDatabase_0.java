	 *            description of index to read. This is an instance previously
	 *            obtained from {@link #listPacks()}, but not necessarily from
	 *            the same DfsObjDatabase instance.
	 * @return channel to read the pack file.
	 * @throws FileNotFoundException
	 *             the file does not exist.
	 * @throws IOException
	 *             the file cannot be opened.
	 */
	protected abstract ReadableChannel openPackIndex(DfsPackDescription desc)
			throws FileNotFoundException, IOException;

	/**
	 * Open a pack file for writing.
	 *
	 * @param desc
	 *            description of pack to write. This is an instance previously
	 *            obtained from {@link #newPack(PackSource)}.
	 * @return channel to write the pack file.
