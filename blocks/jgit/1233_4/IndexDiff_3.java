		boolean changesExist = false;
		DirCache dirCache = repository.readDirCache();
		TreeWalk treeWalk = new TreeWalk(repository);
		treeWalk.reset();
		treeWalk.setRecursive(true);
		treeWalk.addTree(tree);
		treeWalk.addTree(new DirCacheIterator(dirCache));
		treeWalk.addTree(initialWorkingTreeIterator);
		treeWalk.setFilter(TreeFilter.ANY_DIFF);
		while (treeWalk.next()) {
			AbstractTreeIterator treeIterator = treeWalk.getTree(TREE
					AbstractTreeIterator.class);
			DirCacheIterator dirCacheIterator = treeWalk.getTree(INDEX
					DirCacheIterator.class);
			WorkingTreeIterator workingTreeIterator = treeWalk.getTree(WORKDIR
					WorkingTreeIterator.class);
			FileMode fileModeTree = treeWalk.getFileMode(TREE);

			if (treeIterator != null) {
				if (dirCacheIterator != null) {
					if (!treeIterator.getEntryObjectId().equals(
							dirCacheIterator.getEntryObjectId())) {
						changed.add(dirCacheIterator.getEntryPathString());
						changesExist = true;
					}
