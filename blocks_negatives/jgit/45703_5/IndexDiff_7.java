		if (filter != null)
			filters.add(filter);
		filters.add(new SkipWorkTreeFilter(INDEX));
		indexDiffFilter = new IndexDiffFilter(INDEX, WORKDIR);
		filters.add(indexDiffFilter);
		treeWalk.setFilter(AndTreeFilter.create(filters));
		fileModes.clear();
		while (treeWalk.next()) {
			AbstractTreeIterator treeIterator = treeWalk.getTree(TREE,
					AbstractTreeIterator.class);
			DirCacheIterator dirCacheIterator = treeWalk.getTree(INDEX,
					DirCacheIterator.class);
			WorkingTreeIterator workingTreeIterator = treeWalk.getTree(WORKDIR,
					WorkingTreeIterator.class);

			if (dirCacheIterator != null) {
				final DirCacheEntry dirCacheEntry = dirCacheIterator
						.getDirCacheEntry();
				if (dirCacheEntry != null) {
					int stage = dirCacheEntry.getStage();
					if (stage > 0) {
						String path = treeWalk.getPathString();
						addConflict(path, stage);
						continue;
