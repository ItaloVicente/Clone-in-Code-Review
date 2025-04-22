		return cache;
	}

	private static TreeFilter createPathFilter(Collection<String> paths) {
		List<TreeFilter> filters = new ArrayList<TreeFilter>(paths.size());
		for (String path : paths) {
			if (path.length() == 0)
				return null;
			filters.add(PathFilter.create(path));
