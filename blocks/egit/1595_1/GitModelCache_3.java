	protected TreeWalk createAndConfigureTreeWalk() throws IOException {
		TreeWalk tw = createTreeWalk();
		tw.setRecursive(true);

		Repository repo = getRepository();
		DirCache index = repo.readDirCache();
		ObjectId headId = repo.getRef(Constants.HEAD).getObjectId();
		tw.addTree(new RevWalk(repo).parseTree(headId));
		tw.addTree(new DirCacheIterator(index));
		dirCacheIteratorNth = 1;

		return tw;
	}

	private GitModelObject extractFromCache(TreeWalk tw) throws IOException {
		DirCacheIterator cacheIterator = tw.getTree(dirCacheIteratorNth,
