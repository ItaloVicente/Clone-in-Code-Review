	@Override
	protected TreeWalk createAndConfigureTreeWalk() throws IOException {
		TreeWalk tw = createTreeWalk();
		tw.setRecursive(true);

		Repository repo = getRepository();
		int ftIndex = tw.addTree(new AdaptableFileTreeIterator(repo,
				ResourcesPlugin.getWorkspace().getRoot()));
		int dirCacheIteratorNth = tw.addTree(new DirCacheIterator(repo.readDirCache()));
		IndexDiffFilter idf = new IndexDiffFilter(dirCacheIteratorNth, ftIndex, true);

		if (pathFilter != null)
			tw.setFilter(AndTreeFilter.create(pathFilter, idf));
		else
			tw.setFilter(idf);

		return tw;
	}

