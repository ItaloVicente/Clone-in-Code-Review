			mergedAbsoluteFilePath = mergedFilePath;
		}
		Repository repository = null;
		IPath[] paths = new Path[1];
		paths[0] = new Path(mergedAbsoluteFilePath);
		Map<Repository, Collection<String>> pathsByRepository = ResourceUtil
				.splitPathsByRepository(Arrays.asList(paths));
		Set<Repository> repos = pathsByRepository.keySet();
		if (repos.size() >= 1) {
			repository = repos.iterator().next();
		}
		if (repository == null) {
			return;
