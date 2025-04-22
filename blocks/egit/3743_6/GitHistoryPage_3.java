	private TreeFilter createFollowFilterFor(List<String> paths) {
		if (paths == null || paths.isEmpty())
			throw new IllegalArgumentException("paths must not be null nor empty"); //$NON-NLS-1$

		List<TreeFilter> followFilters = new ArrayList<TreeFilter>(paths.size());
		for (String path : paths)
			followFilters.add(FollowFilter.create(path));

		if (followFilters.size() == 1)
			return followFilters.get(0);

		return OrTreeFilter.create(followFilters);
	}

	private boolean allRegularFiles(List<FilterPath> paths) {
		for (FilterPath filterPath : paths) {
			if (!filterPath.isRegularFile())
				return false;
		}
		return true;
	}

