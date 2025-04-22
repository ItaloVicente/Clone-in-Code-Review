		TreeFilter filter = getDiffTreeFilterFor(a
		if (pathFilter instanceof FollowFilter) {
			walk.setFilter(AndTreeFilter.create(
					PathFilter.create(((FollowFilter) pathFilter).getPath())
					filter));
		} else if (pathFilter != null) {
			walk.setFilter(AndTreeFilter.create(pathFilter
		} else {
			walk.setFilter(filter);
		}
