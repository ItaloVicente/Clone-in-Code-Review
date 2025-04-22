		IResource[] resources = getSelectedResources();
		Map<Repository, Collection<String>> pathsByRepository = ResourceUtil.splitResourcesByRepository(resources);

		Set<Repository> repos = pathsByRepository.keySet();

		if (repos.size() != 1)
