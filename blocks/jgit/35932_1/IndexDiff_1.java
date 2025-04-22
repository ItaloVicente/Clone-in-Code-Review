
	public Set<String> getPathsWithIndexMode(final FileMode mode) {
		Set<String> paths = fileModes.get(mode);
		if (paths == null)
			paths = new HashSet<String>();
		return paths;
	}
