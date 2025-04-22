		Collection<TreeFilter> filters = new ArrayList<TreeFilter>();
		if (filter != null)
			filters.add(filter);
		filters.add(new NotIgnoredFilter(WORKDIR));
		filters.add(new SkipWorkTreeFilter(INDEX));
		filters.add(TreeFilter.ANY_DIFF);
		treeWalk.setFilter(AndTreeFilter.create(filters));
