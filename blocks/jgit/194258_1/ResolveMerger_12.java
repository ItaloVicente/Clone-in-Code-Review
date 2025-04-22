		builder = dircache.builder();
		DirCacheBuildIterator buildIt = new DirCacheBuildIterator(builder);

		tw = new NameConflictTreeWalk(db
		tw.addTree(baseTree);
		tw.setHead(tw.addTree(headTree));
		tw.addTree(mergeTree);
		int dciPos = tw.addTree(buildIt);
		if (workingTreeIterator != null) {
			tw.addTree(workingTreeIterator);
			workingTreeIterator.setDirCacheIterator(tw
		} else {
			tw.setFilter(TreeFilter.ANY_DIFF);
		}
