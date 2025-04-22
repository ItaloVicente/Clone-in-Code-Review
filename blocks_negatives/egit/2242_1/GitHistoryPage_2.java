			try {
				if (store
						.getBoolean(UIPreferences.RESOURCEHISTORY_SHOW_ALL_BRANCHES)) {
					markStartAllRefs(Constants.R_HEADS);
					markStartAllRefs(Constants.R_REMOTES);
				} else
					currentWalk.markStart(currentWalk.parseCommit(headId));
			} catch (IOException e) {
				throw new IllegalStateException(NLS.bind(
						UIText.GitHistoryPage_errorReadingHeadCommit, headId,
						db.getDirectory().getAbsolutePath()), e);
			}

			final TreeWalk fileWalker = new TreeWalk(db);
			fileWalker.setRecursive(true);
			if (paths.size() > 0) {
				pathFilters = paths;
				currentWalk.setTreeFilter(AndTreeFilter.create(PathFilterGroup
						.createFromStrings(paths), TreeFilter.ANY_DIFF));
				fileWalker.setFilter(currentWalk.getTreeFilter().clone());
