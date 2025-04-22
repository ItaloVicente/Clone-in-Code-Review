	 * Create an iterator to walk the merge base of two commits.
	 *
	 * @param a
	 *            the first commit in {@link #sourceObjects}.
	 * @param b
	 *            the second commit in {@link #sourceObjects}.
	 * @return the new iterator
	 * @throws IncorrectObjectTypeException
	 *             one of the input objects is not a commit.
	 * @throws IOException
	 *             objects are missing or multiple merge bases were found.
	 * @since 3.0
