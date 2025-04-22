	public void setIncludedPaths(Set<IContainer> includedPaths) {
		this.includedPaths = includedPaths;
		Set<String> paths = new HashSet<String>();
		RepositoryMapping rm = RepositoryMapping.findRepositoryMapping(repo);
		for (IContainer container : includedPaths)
			paths.add(rm.getRepoRelativePath(container));

		if (!paths.isEmpty())
			pathFilter = PathFilterGroup.createFromStrings(paths);
	}

	public Set<IContainer> getIncludedPaths() {
		return includedPaths;
	}

	public TreeFilter getPathFilter() {
		return pathFilter;
	}

