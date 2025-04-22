	/**
	 * Creates and configures {@link TreeWalk} instance for
	 * {@link GitModelCache#getChildrenImpl()} method. It is IMPORTANT to add
	 * tree that will be used as a base as first, remote tree should be added as
	 * second;
	 *
	 * @return configured instance of TreeW
	 * @throws IOException
	 */
	protected TreeWalk createAndConfigureTreeWalk() throws IOException {
		TreeWalk tw = createTreeWalk();
		tw.setRecursive(true);

		Repository repo = getRepository();
		DirCache index = repo.readDirCache();
		ObjectId headId = repo.getRef(Constants.HEAD).getObjectId();
		tw.addTree(new RevWalk(repo).parseTree(headId));
		tw.addTree(new DirCacheIterator(index));

		if (pathFilter != null)
			tw.setFilter(pathFilter);

		return tw;
