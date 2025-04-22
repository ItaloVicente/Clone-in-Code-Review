			final TreeWalk fileWalker = new TreeWalk(db);
			fileWalker.setRecursive(true);
			if (paths.size() > 0) {
				pathFilters = paths;
				currentWalk.setTreeFilter(AndTreeFilter.create(PathFilterGroup
						.createFromStrings(paths), TreeFilter.ANY_DIFF));
				fileWalker.setFilter(currentWalk.getTreeFilter().clone());

			} else {
				pathFilters = null;
				currentWalk.setTreeFilter(TreeFilter.ALL);
				fileWalker.setFilter(TreeFilter.ANY_DIFF);
