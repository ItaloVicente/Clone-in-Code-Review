	public LogCommand excludePath(String path) {
		checkCallable();
		excludeTreeFilters.add(PathFilter.create(path).negate());
		return this;
	}

