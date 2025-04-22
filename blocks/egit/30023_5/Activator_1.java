			RepositoryCache repositoryCache = org.eclipse.egit.core.Activator
					.getDefault().getRepositoryCache();
			if (repositoryCache == null)
				return Status.OK_STATUS;

			Repository[] repos = repositoryCache.getAllRepositories();
