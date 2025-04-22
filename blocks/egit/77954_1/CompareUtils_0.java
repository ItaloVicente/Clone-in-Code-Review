		if (repository == null) {
			RepositoryMapping mapping = RepositoryMapping.getMapping(location);
			if (mapping != null) {
				repository = mapping.getRepository();
				if (repository == null) {
					return mapping.getRepoRelativePath(location);
				}
			}
