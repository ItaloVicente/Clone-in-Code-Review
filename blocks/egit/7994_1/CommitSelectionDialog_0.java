	private TreeFilter createTreeFilter() {
		if (filterResources == null)
			return TreeFilter.ALL;

		List<TreeFilter> filters = new ArrayList<TreeFilter>();
		for (IResource resource : filterResources) {
			RepositoryMapping mapping = RepositoryMapping.getMapping(resource);
			if (mapping != null) {
				String path = mapping.getRepoRelativePath(resource);
				if (resource.getType() == IResource.FILE)
					filters.add(FollowFilter.create(path));
				else
					filters.add(AndTreeFilter.create(PathFilter.create(path), TreeFilter.ANY_DIFF));
			}
		}

		if (filters.isEmpty())
			return TreeFilter.ALL;
		else if (filters.size() == 1)
			return filters.get(0);
		else
			return OrTreeFilter.create(filters);
	}
