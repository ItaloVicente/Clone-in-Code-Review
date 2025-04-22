	private static String getRepoRelativePath(IResource resource) {
		final RepositoryMapping mapping = RepositoryMapping
				.getMapping(resource.getProject());
		if (mapping == null) {
			return null;
		}
		return mapping.getRepoRelativePath(resource);
	}

