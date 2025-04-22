		TreeWalk tw = new TreeWalk(repository);
		try {
			if (filterPaths.size() > 1) {
				List<TreeFilter> suffixFilters = new ArrayList<TreeFilter>();
				for (String filterPath : filterPaths)
					suffixFilters.add(PathFilter.create(filterPath));
				TreeFilter otf = OrTreeFilter.create(suffixFilters);
				tw.setFilter(otf);
			} else if (filterPaths.size() > 0)
				tw.setFilter(PathFilter.create(filterPaths.get(0)));

			tw.setRecursive(true);

			int dirCacheIndex = tw.addTree(new DirCacheIterator(repository
					.readDirCache()));
			int fileTreeIndex = tw.addTree(new FileTreeIterator(repository));
			int repositoryTreeIndex = tw.addTree(rw.parseTree(repository
					.resolve(Constants.HEAD)));

			while (tw.next()) {
				if (monitor.isCanceled())
					throw new InterruptedException();
