		try {
			tw.addTree(new DirCacheIterator(dc));
			tw.addTree(tree);
			tw.setRecursive(true);
			tw.setFilter(PathFilter.create(path));
			while (tw.next()) {
				AbstractTreeIterator dcIt = tw.getTree(0
						DirCacheIterator.class);
				AbstractTreeIterator treeIt = tw.getTree(1
						AbstractTreeIterator.class);
				if (dcIt == null || treeIt == null)
					return true;
				if (dcIt.getEntryRawMode() != treeIt.getEntryRawMode())
					return true;
				if (!dcIt.getEntryObjectId().equals(treeIt.getEntryObjectId()))
					return true;
