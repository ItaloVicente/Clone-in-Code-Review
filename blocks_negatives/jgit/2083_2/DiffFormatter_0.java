		TreeFilter filter = pathFilter;

		if (a instanceof WorkingTreeIterator)
			filter = AndTreeFilter.create(filter, new NotIgnoredFilter(0));
		if (b instanceof WorkingTreeIterator)
			filter = AndTreeFilter.create(filter, new NotIgnoredFilter(1));
		if (!(pathFilter instanceof FollowFilter))
			filter = AndTreeFilter.create(filter, TreeFilter.ANY_DIFF);
		walk.setFilter(filter);
