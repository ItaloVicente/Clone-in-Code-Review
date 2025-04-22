		try (TreeWalk treeWalk = new TreeWalk(repository)) {
			treeWalk.setRecursive(true);
			if (tree != null)
				treeWalk.addTree(tree);
			else
				treeWalk.addTree(new EmptyTreeIterator());
			treeWalk.addTree(new DirCacheIterator(dirCache));
			treeWalk.addTree(initialWorkingTreeIterator);
			Collection<TreeFilter> filters = new ArrayList<TreeFilter>(4);

			if (monitor != null) {
				if (estIndexSize == 0)
					estIndexSize = dirCache.getEntryCount();
				int total = Math.max(estIndexSize * 10 / 9
						estWorkTreeSize * 10 / 9);
				monitor.beginTask(title
				filters.add(new ProgressReportingFilter(monitor
			}
