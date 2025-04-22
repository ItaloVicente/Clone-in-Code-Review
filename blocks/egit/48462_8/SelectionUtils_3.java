	@Nullable
	private static Repository getRepository(IResource resource) {
		IPath location = resource.getLocation();
		if (location != null) {
			RepositoryMapping repositoryMapping = RepositoryMapping
					.getMapping(location);
			if (repositoryMapping != null) {
				return repositoryMapping.getRepository();
			}
		}
		return null;
	}

