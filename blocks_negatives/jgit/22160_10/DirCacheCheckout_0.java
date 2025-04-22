		tw.addTree(new DirCacheIterator(dc));
		tw.addTree(new FileTreeIterator(repo));
		tw.setRecursive(true);
		tw.setFilter(PathFilter.create(path));
		DirCacheIterator dcIt;
		WorkingTreeIterator wtIt;
		while(tw.next()) {
			dcIt = tw.getTree(0, DirCacheIterator.class);
			wtIt = tw.getTree(1, WorkingTreeIterator.class);
			if (dcIt == null || wtIt == null)
				return true;
			if (wtIt.isModified(dcIt.getDirCacheEntry(), true,
					this.walk.getObjectReader())) {
				return true;
