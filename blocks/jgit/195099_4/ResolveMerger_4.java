		try {
			workTreeUpdater = inCore ?
					WorkTreeUpdater.createInCoreWorkTreeUpdater(db
					WorkTreeUpdater.createWorkTreeUpdater(db
			dircache = workTreeUpdater.getLockedDirCache();
			tw = new NameConflictTreeWalk(db

			tw.addTree(baseTree);
			tw.setHead(tw.addTree(headTree));
			tw.addTree(mergeTree);
			DirCacheBuildIterator buildIt = workTreeUpdater.createDirCacheBuildIterator();
			int dciPos = tw.addTree(buildIt);
			if (workingTreeIterator != null) {
				tw.addTree(workingTreeIterator);
				workingTreeIterator.setDirCacheIterator(tw
			} else {
				tw.setFilter(TreeFilter.ANY_DIFF);
			}
