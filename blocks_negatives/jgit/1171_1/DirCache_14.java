	/**
	 * Create a new in-core index representation and read an index from disk.
	 * <p>
	 * The new index will be read before it is returned to the caller. Read
	 * failures are reported as exceptions and therefore prevent the method from
	 * returning a partially populated index.
	 *
	 * @param db
	 *            repository the caller wants to read the default index of.
	 * @return a cache representing the contents of the specified index file (if
	 *         it exists) or an empty cache if the file does not exist.
	 * @throws IOException
	 *             the index file is present but could not be read.
	 * @throws CorruptObjectException
	 *             the index file is using a format or extension that this
	 *             library does not support.
	 */
	public static DirCache read(final Repository db)
			throws CorruptObjectException, IOException {
		return read(new File(db.getDirectory(), "index"));
	}

