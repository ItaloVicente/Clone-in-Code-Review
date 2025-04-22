		TreeFilter filter = getDiffTreeFilterFor(a
		if (pathFilter instanceof FollowFilter) {
			walk.setFilter(AndTreeFilter.create(
					PathFilter.create(((FollowFilter) pathFilter).getPath())
					filter));
		} else {
			walk.setFilter(AndTreeFilter.create(pathFilter
		}
