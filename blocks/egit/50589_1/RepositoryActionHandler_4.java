		RepositoryMapping mapping = RepositoryMapping.getMapping(resource.getProject());
		if (mapping == null) {
			return result;
		}
		String path = mapping.getRepoRelativePath(resource);
		if (path == null) {
			return result;
		}
