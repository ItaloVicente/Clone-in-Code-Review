		if (pathFilter == TreeFilter.ALL) {
			walk.setFilter(TreeFilter.ANY_DIFF);
		} else if (pathFilter instanceof FollowFilter) {
			walk.setFilter(pathFilter);
		} else {
			walk.setFilter(AndTreeFilter
					.create(pathFilter, TreeFilter.ANY_DIFF));
		}
