	/**
	 * Helper method to create a new tree walk between the repository, the
	 * index, and the working tree.
	 *
	 * @return the created tree walk, or null if it could not be created
	 * @throws IOException
	 *             if there were errors when creating the tree walk
	 */
	private TreeWalk createThreeWayTreeWalk() throws IOException {
		final TreeWalk treeWalk = new TreeWalk(repository);
		if (!addResourceFilter(treeWalk, resource))
			return null;

		treeWalk.setRecursive(treeWalk.getFilter().shouldBeRecursive());
		treeWalk.reset();

		if (headId != null)
			treeWalk.addTree(new RevWalk(repository).parseTree(headId));
		else
			treeWalk.addTree(new EmptyTreeIterator());
