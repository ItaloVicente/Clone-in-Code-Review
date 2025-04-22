	/**
	 * Get the BitmapIndex for this PackFile.
	 *
	 * @param ctx
	 *            reader context to support reading from the backing store if
	 *            the index is not already loaded in memory.
	 * @return the BitmapIndex.
	 * @throws java.io.IOException
	 *             the bitmap index is not available, or is corrupt.
	 */
	public PackBitmapIndex getBitmapIndex(DfsReader ctx) throws IOException {
