	private TreeFilter createPathFilter(final Collection<? extends IResource> rs) {
		if (rs == null || rs.isEmpty())
			return null;
		final List<PathFilter> filters = new ArrayList<PathFilter>();
		for (IResource r : rs) {
			RepositoryMapping rm = RepositoryMapping.getMapping(r);
			String repoRelativePath = rm.getRepoRelativePath(r);
			if (repoRelativePath != null)
				filters.add(PathFilter.create(repoRelativePath));
		}
		if (filters.size() == 0)
			return null;
		if (filters.size() == 1)
			return filters.get(0);
		return PathFilterGroup.create(filters);
	}

