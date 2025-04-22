			if (repositoryMapping == null) {
				Repository repository = org.eclipse.egit.core.Activator
						.getDefault().getRepositoryCache()
						.getRepository(location);
				return repository;
			}
			if (mapping == null)
