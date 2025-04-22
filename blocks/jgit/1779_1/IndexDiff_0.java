		if (filter == null)
			treeWalk.setFilter(AndTreeFilter.create(new TreeFilter[] {
					new NotIgnoredFilter(WORKDIR)
					new SkipWorkTreeFilter(INDEX)
		else
			treeWalk.setFilter(AndTreeFilter
					.create(new TreeFilter[] { new NotIgnoredFilter(WORKDIR)
							new SkipWorkTreeFilter(INDEX)
							filter }));
