		if (input instanceof IResource) {
			IResource resource = (IResource) input;
			RepositoryMapping mapping = RepositoryMapping.getMapping(resource);
			if (mapping != null) {
				return mapping.getRepository();
			}
			Repository repository = Activator.getDefault().getRepositoryCache()
					.getRepository(resource);
			if (repository != null) {
				return repository;
			}
		}
