		if (store.getBoolean(UIPreferences.RESOURCEHISTORY_FOLLOW_RENAMES)
				&& paths.size() == 1
				&& paths.get(0).isRegularFile()) {
			pathFilters = paths;
			currentWalk.setTreeFilter(FollowFilter.create(paths.get(0).getPath()));
		} else if (paths.size() > 0) {
