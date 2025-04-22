		TreeFilter filter = pathFilter;

		if (a instanceof WorkingTreeIterator)
			filter = AndTreeFilter.create(filter
		if (b instanceof WorkingTreeIterator)
			filter = AndTreeFilter.create(filter
		if (!(pathFilter instanceof FollowFilter))
			filter = AndTreeFilter.create(filter
		walk.setFilter(filter);

		source = new ContentSource.Pair(source(a)
