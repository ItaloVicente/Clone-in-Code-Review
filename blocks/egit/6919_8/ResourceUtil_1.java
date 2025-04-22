			addPathToMap(repositoryMapping, path, result);
		}
		return result;
	}

	public static Map<Repository, Collection<String>> splitPathsByRepository(
			Collection<IPath> paths) {
		Map<Repository, Collection<String>> result = new HashMap<Repository, Collection<String>>();
		for (IPath path : paths) {
			RepositoryMapping repositoryMapping = RepositoryMapping.getMapping(path);
			if (repositoryMapping == null)
				continue;
			String p = repositoryMapping.getRepoRelativePath(path);
			addPathToMap(repositoryMapping, p, result);
