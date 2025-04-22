	private void resetIndex(RevTree tree) throws IOException {
		DirCache dc = repo.lockDirCache();
		TreeWalk walk = null;
		try {
			DirCacheBuilder builder = dc.builder();

			walk = new TreeWalk(repo);
			walk.addTree(tree);
			walk.addTree(new DirCacheIterator(dc));
			walk.setRecursive(true);

			while (walk.next()) {
				AbstractTreeIterator cIter = walk.getTree(0
						AbstractTreeIterator.class);
				if (cIter == null) {
					continue;
				}
