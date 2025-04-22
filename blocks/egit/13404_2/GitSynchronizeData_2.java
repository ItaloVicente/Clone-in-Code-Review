	private void setIncludedResources(Set<IResource> includedResources) {
		this.includedPaths = includedResources;
		Set<String> paths = new LinkedHashSet<String>(includedResources.size());
		RepositoryMapping rm = RepositoryMapping.findRepositoryMapping(repo);
		for (IResource resource : includedResources) {
			String repoRelativePath = rm.getRepoRelativePath(resource);
			if (repoRelativePath.length() > 0)
				paths.add(repoRelativePath);
		}

		if (!paths.isEmpty())
			pathFilter = PathFilterGroup.createFromStrings(paths);
	}

