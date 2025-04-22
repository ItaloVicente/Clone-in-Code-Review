	public LogCommand addPath(String path) {
		checkCallable();
		pathFilters.add(PathFilter.create(path));
		return this;
	}

