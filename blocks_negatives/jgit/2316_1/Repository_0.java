	/**
	 * Add a single existing pack to the list of available pack files.
	 *
	 * @param pack
	 *            path of the pack file to open.
	 * @param idx
	 *            path of the corresponding index file.
	 * @throws IOException
	 *             index file could not be opened, read, or is not recognized as
	 *             a Git pack file index.
	 */
	public abstract void openPack(File pack, File idx) throws IOException;

