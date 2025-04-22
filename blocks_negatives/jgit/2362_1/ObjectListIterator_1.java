	 *
	 * @return next most recent commit; null if traversal is over.
	 * @throws IOException
	 *             the list cannot be read.
	 */
	public abstract RevCommit next() throws IOException;

	/**
	 * Pop the next most recent object.
