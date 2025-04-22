	private static TreeFilter createPathFilter(Collection<String> paths) {
		if (paths.isEmpty()
				|| paths.stream().anyMatch(s -> s == null || s.isEmpty())) {
			return null;
		}
		return PathFilterGroup.createFromStrings(paths);
	}

