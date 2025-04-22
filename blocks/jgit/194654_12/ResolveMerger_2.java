		try {
			ioHandler = inCore ?
					RepoIOHandler.initRepoIOHandlerForRemote(db
					RepoIOHandler.initRepoIOHandlerForLocal(db
			dircache = ioHandler.getLockedDirCache();
			tw = new NameConflictTreeWalk(db

			tw.addTree(baseTree);
			tw.setHead(tw.addTree(headTree));
			tw.addTree(mergeTree);
			DirCacheBuildIterator buildIt = ioHandler.createDirCacheBuildIterator();
			int dciPos = tw.addTree(buildIt);
			if (workingTreeIterator != null) {
				tw.addTree(workingTreeIterator);
				workingTreeIterator.setDirCacheIterator(tw
			} else {
				tw.setFilter(TreeFilter.ANY_DIFF);
			}
