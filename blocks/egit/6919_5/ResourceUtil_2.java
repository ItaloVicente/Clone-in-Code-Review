	private static void addPathToMap(RepositoryMapping repositoryMapping,
			String path, Map<Repository, Collection<String>> result) {
		if (path != null && path.length() > 0) {
			Repository repository = repositoryMapping.getRepository();
			Collection<String> resourcesList = result.get(repository);
			if (resourcesList == null) {
				resourcesList = new ArrayList<String>();
				result.put(repository, resourcesList);
			}
			resourcesList.add(path);
		}
	}
