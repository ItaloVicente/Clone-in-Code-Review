	private TreeFilter filterPaths(Collection<String> paths) {
		if (paths.isEmpty()) {
			return null;
		}
		return PathFilterGroup.createFromStrings(paths);
	}

