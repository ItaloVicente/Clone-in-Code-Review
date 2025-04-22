	private static TreeFilter createPathFilter(Collection<String> paths) {
		List<TreeFilter> filters = new ArrayList<TreeFilter>(paths.size());
		for (String path : paths)
			filters.add(PathFilter.create(path));
		if (filters.size() == 1)
			return filters.get(0);
		return OrTreeFilter.create(filters);
	}

