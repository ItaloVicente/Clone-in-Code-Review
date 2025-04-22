	protected TreeWalk createTreeWalk() {
		TreeWalk tw = new TreeWalk(getRepository());

		tw.reset();
		tw.setRecursive(false);
		tw.setFilter(TreeFilter.ANY_DIFF);

		return tw;
	}

	/**
	 * Return list of not ignored children in root node.
	 *
	 * @param root
	 * @return list of not ignored nodes
	 * @throws IOException
	 */
	protected List<String> getNotIgnoredNodes(ObjectId root) throws IOException {
