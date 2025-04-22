	 * @param fd
	 *            stream to read the index file from. The stream must be
	 *            buffered as some small IOs are performed against the stream.
	 *            The caller is responsible for closing the stream.
	 * @return a copy of the index in-memory.
	 * @throws java.io.IOException
	 *             the stream cannot be read.
	 * @throws org.eclipse.jgit.errors.CorruptObjectException
	 *             the stream does not contain a valid pack index.
