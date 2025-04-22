	 *             the {@link #getEntries()} method has already been called for
	 *             this instance.
	 * @throws MissingObjectException
	 *             {@link TreeWalk#isRecursive()} was enabled on the tree, a
	 *             subtree was found, but the subtree object does not exist in
	 *             this repository. The repository may be missing objects.
	 * @throws IncorrectObjectTypeException
	 *             {@link TreeWalk#isRecursive()} was enabled on the tree, a
	 *             subtree was found, and the subtree id does not denote a tree,
	 *             but instead names some other non-tree type of object. The
	 *             repository may have data corruption.
	 * @throws CorruptObjectException
	 *             the contents of a tree did not appear to be a tree. The
	 *             repository may have data corruption.
	 * @throws IOException
	 *             a loose object or pack file could not be read.
