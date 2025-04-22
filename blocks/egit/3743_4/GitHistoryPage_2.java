		if (store.getBoolean(UIPreferences.RESOURCEHISTORY_FOLLOW_RENAMES)
				&& paths.size() == 1
				&& paths.get(0).isRegularFile()) {
			pathFilters = paths;
			String selectedPath = paths.get(0).getPath();
			currentWalk.setTreeFilter(FollowFilter.create(selectedPath));
			fileWalker.setFilter(AndTreeFilter.create(PathFilterGroup
					.createFromStrings(selectedPath), TreeFilter.ANY_DIFF));
		} else if (paths.size() > 0) {
