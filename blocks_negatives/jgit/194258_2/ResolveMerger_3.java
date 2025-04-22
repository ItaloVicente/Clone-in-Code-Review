		try {
			workTreeUpdater = inCore ?
					WorkTreeUpdater.createInCoreWorkTreeUpdater(db, dircache, getObjectInserter()) :
					WorkTreeUpdater.createWorkTreeUpdater(db, dircache);
			dircache = workTreeUpdater.getLockedDirCache();
			tw = new NameConflictTreeWalk(db, reader);

			tw.addTree(baseTree);
			tw.setHead(tw.addTree(headTree));
			tw.addTree(mergeTree);
			DirCacheBuildIterator buildIt = workTreeUpdater.createDirCacheBuildIterator();
			int dciPos = tw.addTree(buildIt);
			if (workingTreeIterator != null) {
				tw.addTree(workingTreeIterator);
				workingTreeIterator.setDirCacheIterator(tw, dciPos);
			} else {
				tw.setFilter(TreeFilter.ANY_DIFF);
			}
